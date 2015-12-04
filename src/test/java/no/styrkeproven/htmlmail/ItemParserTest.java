package no.styrkeproven.htmlmail;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import no.styrkeproven.htmlmail.item.Item;
import no.styrkeproven.htmlmail.item.ItemParser;
import no.styrkeproven.htmlmail.item.Items;

public class ItemParserTest
    extends TestCase
{

    public void testParser()
        throws Exception
    {
        Map<String, String[]> paramsMap = new HashMap<String, String[]>();

        paramsMap.put( "Fornavn", new String[]{"test"} );
        paramsMap.put( "cc", new String[]{"runar@myklebust.me"} );

        paramsMap.put( "Team_Jersey", new String[]{"Team Jersey"} );
        paramsMap.put( "Team_Jersey_size", new String[]{"NONE"} );
        paramsMap.put( "Team_Jersey_amount", new String[]{"0"} );
        paramsMap.put( "Team_Jersey_price", new String[]{"720"} );

        paramsMap.put( "Womens_Team_Jersey", new String[]{"Womens Team Jersey"} );
        paramsMap.put( "Womens_Team_Jersey_size", new String[]{"NONE"} );
        paramsMap.put( "Womens_Team_Jersey_amount", new String[]{"1"} );
        paramsMap.put( "Womens_Team_Jersey_price", new String[]{"1420"} );

        paramsMap.put( "fisk", new String[]{"ost"} );
        paramsMap.put( "fisk_size", new String[]{"23"} );

        final Items items = ItemParser.create( paramsMap );

        assertItem( items, "Team Jersey", 0, 720, "NONE" );
        assertItem( items, "Womens Team Jersey", 1, 1420, "NONE" );
    }

    private void assertItem( final Items items, final String name, final int amount, final double price, final String size )
    {
        final Item item = items.get( name );
        assertNotNull( item );
        assertEquals( name, item.getDisplayName() );
        assertEquals( size, item.getSize() );
        assertEquals( amount, item.getAmount() );
        assertEquals( price, item.getPrice() );
    }
}