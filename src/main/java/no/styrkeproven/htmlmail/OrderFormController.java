package no.styrkeproven.htmlmail;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.jdom.Document;

import com.google.common.collect.Lists;

import no.styrkeproven.htmlmail.content.ContentTypeParser;
import no.styrkeproven.htmlmail.content.CreateContentParamsFactory;
import no.styrkeproven.htmlmail.content.InputTypeDef;
import no.styrkeproven.htmlmail.item.ItemParser;
import no.styrkeproven.htmlmail.item.Items;
import no.styrkeproven.htmlmail.order.OrderSummary;
import no.styrkeproven.htmlmail.order.OrderSummaryFactory;

import com.enonic.cms.api.client.Client;
import com.enonic.cms.api.client.ClientFactory;
import com.enonic.cms.api.client.model.CreateContentParams;
import com.enonic.cms.api.client.model.GetContentTypeConfigXMLParams;
import com.enonic.cms.api.plugin.ext.http.HttpInterceptor;

public class OrderFormController
    extends HttpInterceptor
{
    private String smtpHost;

    private String mailFrom;

    private String mailFromName;

    private Client client;

    public final void init()
    {
        checkOption( this.smtpHost, "smtpHost" );
        checkOption( this.mailFrom, "mailFrom" );
        checkOption( this.mailFromName, "mailFromName" );
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean preHandle( final HttpServletRequest request, final HttpServletResponse httpServletResponse )
        throws Exception
    {
        this.client = ClientFactory.getLocalClient();

        final Map<String, String[]> parameterMap = (Map<String, String[]>) request.getParameterMap();

        verifyParameter( "categoryKey", parameterMap );

        doCreateContent( parameterMap );

        doSendMail( request, parameterMap );

        return true;
    }

    @Override
    public void postHandle( final HttpServletRequest request, final HttpServletResponse httpServletResponse )
        throws Exception
    {
        final String[] redirUrl = request.getParameterValues( "redir" );

        httpServletResponse.sendRedirect( redirUrl[0] );
    }

    private void verifyParameter( final String name, Map<String, String[]> parameters )
    {
        if ( !parameters.containsKey( name ) )
        {
            throw new IllegalArgumentException( "Missing parameter: " + name );
        }
    }

    private void doCreateContent( final Map<String, String[]> parameterMap )
    {
        List<InputTypeDef> inputTypeDefs = getInputTypeDefs();

        final String[] categoryKeys = parameterMap.get( "categoryKey" );
        final CreateContentParams createContentParams =
            CreateContentParamsFactory.create( parameterMap, inputTypeDefs, new Integer( categoryKeys[0] ) );

        this.client.createContent( createContentParams );
    }

    private List<InputTypeDef> getInputTypeDefs()
    {
        List<InputTypeDef> inputTypeDefs = Lists.newArrayList();

        final GetContentTypeConfigXMLParams getContentParams = new GetContentTypeConfigXMLParams();
        getContentParams.name = CreateContentParamsFactory.CONTENT_TYPE_NAME;

        final Document contentTypeConfigXML = client.getContentTypeConfigXML( getContentParams );

        ContentTypeParser.parseInputTypeElements( inputTypeDefs, contentTypeConfigXML.getRootElement() );

        System.out.println( "InputTypeDefs: " + inputTypeDefs.size() );

        return inputTypeDefs;
    }

    private void doSendMail( final HttpServletRequest request, final Map<String, String[]> parameterMap )
        throws Exception
    {
        final Items items = ItemParser.create( parameterMap );

        MailSender.create().
            mailFrom( this.mailFrom ).
            smtpHost( this.smtpHost ).
            mailFromName( this.mailFromName ).
            mailMessage( createMessage( items ) ).
            mailSubject( "Kvittering" ).
            mailTo( getStringParam( request, "cc", true ) ).
            build().
            send();
    }

    private String createMessage( final Items items )
    {
        final TemplateFactory templateFactory = new TemplateFactory();

        final OrderSummary orderSummary = OrderSummaryFactory.create( items );

        try
        {
            String template = IOUtils.toString( this.getClass().getResourceAsStream( "template.html" ), "UTF-8" );
            return templateFactory.create( orderSummary, template );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return "Something failed, using backup" + createBackupMessage( items );
        }
    }

    private String createBackupMessage( final Items items )
    {
        StringBuilder builder = new StringBuilder();

        builder.append( "<html>" );
        builder.append( "<body>" );
        builder.append( "Takk for bestillingen!</BR>" );
        builder.append( "Ordered items: " + items.size() );
        builder.append( items.toHtml() );
        builder.append( "</body" );
        builder.append( "</html>" );

        return builder.toString();

    }

    private String getStringParam( final HttpServletRequest request, final String name, final boolean required )
    {
        final String parameter = request.getParameter( name );

        if ( parameter == null && required )
        {
            throw new IllegalArgumentException( "Missing required parameter " + name );
        }

        return parameter;
    }

    private void checkOption( final Object value, final String option )
    {
        if ( value == null )
        {
            throw new IllegalArgumentException( "Option: " + option + " not set correctly" );
        }

        System.out.println( "***** Plugin option registered: " + option + ": " + value );
    }

    public void setSmtpHost( final String smtpHost )
    {
        this.smtpHost = smtpHost;
    }

    public void setMailFromName( final String mailFromName )
    {
        this.mailFromName = mailFromName;
    }

    public void setMailFrom( final String mailFrom )
    {
        this.mailFrom = mailFrom;
    }
}
