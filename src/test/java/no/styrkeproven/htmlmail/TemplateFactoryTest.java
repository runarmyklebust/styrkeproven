package no.styrkeproven.htmlmail;

import org.apache.commons.io.IOUtils;

import junit.framework.TestCase;
import no.styrkeproven.htmlmail.item.Item;
import no.styrkeproven.htmlmail.item.Items;
import no.styrkeproven.htmlmail.order.OrderSummary;
import no.styrkeproven.htmlmail.order.OrderSummaryFactory;

public class TemplateFactoryTest
    extends TestCase
{

    public void testItems()
        throws Exception
    {

        final TemplateFactory templateFactory = new TemplateFactory();

        final Items items = new Items();

        items.add( Item.create().
            amount( 10 ).
            price( 10.0 ).
            displayName( "myProduct" ).
            size( "XL" ).
            build() );

        items.add( Item.create().
            amount( 12 ).
            price( 45.0 ).
            displayName( "myOtherProduct" ).
            size( "Fisk" ).
            build() );

        final String result =
            templateFactory.create( items, IOUtils.toString( this.getClass().getResourceAsStream( "template.html" ), "UTF-8" ) );

        System.out.println( result );
    }

    public void testOrderSummary()
        throws Exception
    {

        final TemplateFactory templateFactory = new TemplateFactory();

        final Items items = new Items();

        items.add( Item.create().
            amount( 10 ).
            price( 10.0 ).
            displayName( "myProduct" ).
            size( "XL" ).
            build() );

        items.add( Item.create().
            amount( 12 ).
            price( 45.0 ).
            displayName( "myOtherProduct" ).
            size( "Fisk" ).
            build() );

        final OrderSummary orderSummary = OrderSummaryFactory.create( items );

        final String result =
            templateFactory.create( orderSummary, IOUtils.toString( this.getClass().getResourceAsStream( "template.html" ), "UTF-8" ) );

        System.out.println( result );
    }
}