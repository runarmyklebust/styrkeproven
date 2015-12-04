package no.styrkeproven.htmlmail.content;

import java.io.InputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;

import com.google.common.collect.Lists;

import junit.framework.TestCase;

public class ContentTypeParserTest
    extends TestCase
{

    public void testParseContentTypeXml()
        throws Exception
    {
        List<InputTypeDef> inputTypeDefList = Lists.newArrayList();

        final Document document = readDocument( "contentType.xml" );

        ContentTypeParser.parseInputTypeElements( inputTypeDefList, document.getRootElement() );

        inputTypeDefList.size();
    }

    private Document readDocument( final String fileName )
        throws Exception
    {
        final InputStream resourceAsStream = this.getClass().getResourceAsStream( fileName );

        SAXBuilder sb = new SAXBuilder();

        return sb.build( resourceAsStream );
    }
}