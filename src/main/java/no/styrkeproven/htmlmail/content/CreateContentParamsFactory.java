package no.styrkeproven.htmlmail.content;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.enonic.cms.api.client.model.CreateContentParams;
import com.enonic.cms.api.client.model.content.ContentDataInput;
import com.enonic.cms.api.client.model.content.ContentStatus;
import com.enonic.cms.api.client.model.content.DateInput;
import com.enonic.cms.api.client.model.content.Input;
import com.enonic.cms.api.client.model.content.SelectorInput;
import com.enonic.cms.api.client.model.content.TextInput;

public class CreateContentParamsFactory
{
    public final static String CONTENT_TYPE_NAME = "styrk-order";

    private final static SimpleDateFormat FORM_DATE_FORMAT = new SimpleDateFormat( "dd.MM.YYYY" );

    public static CreateContentParams create( final Map<String, String[]> params, List<InputTypeDef> inputTypeDefs,
                                              final Integer categoryKey )
    {
        final CreateContentParams createContentParams = new CreateContentParams();

        ContentDataInput contentDataInput = new ContentDataInput( CONTENT_TYPE_NAME );

        for ( final InputTypeDef inputTypeDef : inputTypeDefs )
        {
            final String inputName = inputTypeDef.getName();

            if ( params.containsKey( inputName ) )
            {
                final Input input = createInput( params, inputTypeDef, inputName );

                contentDataInput.add( input );
            }
        }

        createContentParams.categoryKey = categoryKey;
        createContentParams.publishFrom = new java.util.Date();
        createContentParams.changeComment = "automatic generated";
        createContentParams.contentData = contentDataInput;
        createContentParams.status = ContentStatus.STATUS_APPROVED;

        return createContentParams;
    }

    private static Input createInput( final Map<String, String[]> params, final InputTypeDef inputTypeDef, final String inputName )
    {
        if ( inputTypeDef.getInputType().equals( "text" ) )
        {
            final String[] values = params.get( inputName );
            return new TextInput( inputTypeDef.getName(), values[0] );
        }

        if ( inputTypeDef.getInputType().equals( "dropdown" ) )
        {
            final String[] values = params.get( inputName );
            return new SelectorInput( inputTypeDef.getName(), values[0] );
        }

        if ( inputTypeDef.getInputType().equals( "radiobutton" ) )
        {
            final String[] values = params.get( inputName );
            return new SelectorInput( inputTypeDef.getName(), values[0] );
        }

        if ( inputTypeDef.getInputType().equals( "date" ) )
        {
            final String[] values = params.get( inputName );
            try
            {
                return new DateInput( inputTypeDef.getName(), FORM_DATE_FORMAT.parse( values[0] ) );
            }
            catch ( ParseException e )
            {
                throw new IllegalArgumentException( "Wrong format on date from form, expected: " + FORM_DATE_FORMAT.toPattern() );
            }
        }

        throw new IllegalArgumentException( "Unsupported input-type: " + inputTypeDef.getInputType() );
    }
}
