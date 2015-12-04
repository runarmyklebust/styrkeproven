package no.styrkeproven.htmlmail.order;

import java.util.ArrayList;
import java.util.List;

import no.styrkeproven.htmlmail.item.Item;
import no.styrkeproven.htmlmail.item.Items;

public class OrderSummaryFactory
{
    public static OrderSummary create( final Items items )
    {
        final List<Item> boughtProducts = getBoughtProducts( items );

        return OrderSummary.create().
            items( boughtProducts ).
            sum( calculateSum( boughtProducts ) ).
            numberOfProducts( boughtProducts.size() ).
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

    private static double calculateSum( final List<Item> items )
    {
        double sum = 0;

        for ( final Item item : items )
        {
            sum += item.getPrice() * item.getAmount();
        }

        return sum;
    }

}
