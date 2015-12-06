package no.styrkeproven.htmlmail.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import no.styrkeproven.htmlmail.item.Item;
import no.styrkeproven.htmlmail.item.Items;

public class OrderSummaryFactory
{
    public static OrderSummary create( final Items items, Map<String, String[]> parameterMap )
    {
        final List<Item> boughtProducts = getBoughtProducts( items );

        return OrderSummary.create().
            items( boughtProducts ).
            sum( calculateSum( boughtProducts ) ).
            numberOfProducts( boughtProducts.size() ).
            setParameterMap( parameterMap ).
            build();
    }

    private static List<Item> getBoughtProducts( final Items items )
    {
        final List<Item> boughtProducts = new ArrayList<Item>();

        for ( final Item item : items )
        {
            if ( item.isBought() )
            {
                boughtProducts.add( item );
            }
        }

        return boughtProducts;
    }

    private static int calculateSum( final List<Item> items )
    {
        int sum = 0;

        for ( final Item item : items )
        {
            sum += item.getPrice() * item.getAmount();
        }

        return sum;
    }

}
