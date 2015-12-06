package no.styrkeproven.htmlmail;

import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.Maps;

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
            price( 10 ).
            displayName( "myProduct" ).
            size( "XL" ).
            build() );

        items.add( Item.create().
            amount( 12 ).
            price( 45 ).
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
            price( 10 ).
            displayName( "myProduct" ).
            size( "XL" ).
            build() );

        items.add( Item.create().
            amount( 12 ).
            price( 45 ).
            displayName( "myOtherProduct" ).
            size( "Fisk" ).
            build() );

        final Map<String, String[]> paramsMap = Maps.newHashMap();

        paramsMap.put( "Etternavn", new String[]{"Myklebust"} );
        paramsMap.put( "Fornavn", new String[]{"Myklebust"} );
        paramsMap.put( "Adresse", new String[]{"Olav Nygards vei 192"} );
        paramsMap.put( "Postnr", new String[]{"0688"} );
        paramsMap.put( "By", new String[]{"Oslo"} );
        paramsMap.put( "Email", new String[]{"runar@myklebust.me"} );

        final OrderSummary orderSummary = OrderSummaryFactory.create( items, paramsMap );

        final String result =
            templateFactory.create( orderSummary, IOUtils.toString( this.getClass().getResourceAsStream( "template.html" ), "UTF-8" ) );

        System.out.println( result );
    }
}