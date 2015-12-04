package no.styrkeproven.htmlmail;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import no.styrkeproven.htmlmail.item.ItemParser;
import no.styrkeproven.htmlmail.item.Items;
import no.styrkeproven.htmlmail.order.OrderSummary;
import no.styrkeproven.htmlmail.order.OrderSummaryFactory;

import com.enonic.cms.api.plugin.ext.http.HttpController;

public class OrderFormController
    extends HttpController
{
    private String smtpHost;

    private String mailFrom;

    private String mailFromName;

    public final void init()
    {
        checkOption( this.smtpHost, "smtpHost" );
        checkOption( this.mailFrom, "mailFrom" );
        checkOption( this.mailFromName, "mailFromName" );
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handleRequest( final HttpServletRequest request, final HttpServletResponse response )
        throws Exception
    {

        final Items items = ItemParser.create( (Map<String, String[]>) request.getParameterMap() );

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
