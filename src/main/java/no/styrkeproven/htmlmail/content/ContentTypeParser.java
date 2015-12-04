package no.styrkeproven.htmlmail.content;

import java.util.List;

import org.jdom.Element;

public class ContentTypeParser
{
    public static void parseInputTypeElements( final List<InputTypeDef> inputTypeDefs, final Element parent )
    {
        final List<Element> children = parent.getChildren();

        for ( final Element child : children )
        {
            if ( child.getName().equals( "input" ) )
            {
                final String inputName = child.getAttribute( "name" ).getValue();
                final String inputType = child.getAttribute( "type" ).getValue();

                inputTypeDefs.add( new InputTypeDef( inputType, inputName ) );
            }
            else
            {
                parseInputTypeElements( inputTypeDefs, child );
            }
        }
    }

}
