package no.styrkeproven.htmlmail.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ItemParser
{
    private final static String SIZE_POSTFIX = "_size";

    private final static String AMOUNT_POSTFIX = "_amount";

    private final static String PRICE_POSTFIX = "_price";

    public static Items create( final Map<String, String[]> parameters )
    {
        final Items items = new Items();

        final List<String> productItems = getProductItems( parameters );

        for ( final String productItem : productItems )
        {
            items.add( Item.create().
                amount( getSingleIntValue( parameters, productItem + AMOUNT_POSTFIX ) ).
                displayName( getSingleStringValue( parameters, productItem ) ).
                price( new Double( getSingleStringValue( parameters, productItem + PRICE_POSTFIX ) ) ).
                size( getSingleStringValue( parameters, productItem + SIZE_POSTFIX ) ).
                build() );
        }

        return items;
    }

    private static String getSingleStringValue( final Map<String, String[]> parameters, final String productItem )
    {
        final String[] strings = parameters.get( productItem );

        if ( strings.length == 0 )
        {
            throw new RuntimeException( "Unexpected value for parameter: " + productItem + ", should be single string-value" );
        }
        return strings[0];
    }

    private static Integer getSingleIntValue( final Map<String, String[]> parameters, final String paramName )
    {
        return new Integer( getSingleStringValue( parameters, paramName ) );
    }

    private static List<String> getProductItems( final Map<String, String[]> parameters )
    {
        final List<String> foundItems = new ArrayList<String>();

        final Set<String> keys = parameters.keySet();

        for ( final String key : keys )
        {
            if ( isProductSubItem( key ) )
            {
                continue;
            }

            if ( hasProductSubItem( key, parameters ) )
            {
                foundItems.add( key );
            }
        }

        return foundItems;
    }

    private static boolean hasProductSubItem( final String key, final Map<String, String[]> parameters )
    {
        return parameters.containsKey( key + SIZE_POSTFIX ) &&
            parameters.containsKey( key + AMOUNT_POSTFIX ) &&
            parameters.containsKey( key + PRICE_POSTFIX );
    }

    private static boolean isProductSubItem( final String key )
    {
        return key.endsWith( SIZE_POSTFIX ) || key.endsWith( AMOUNT_POSTFIX ) || key.endsWith( PRICE_POSTFIX );
    }

}
