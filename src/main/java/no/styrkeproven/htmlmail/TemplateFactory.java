package no.styrkeproven.htmlmail;

import java.io.StringWriter;
import java.util.List;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import no.styrkeproven.htmlmail.item.Item;
import no.styrkeproven.htmlmail.item.Items;
import no.styrkeproven.htmlmail.order.OrderSummary;


public class TemplateFactory
{

    public TemplateFactory()
    {

    }

    public String create( final Items itemsIn, final String template )
        throws Exception
    {
        Template mustacheTemplate = Mustache.compiler().escapeHTML( false ).compile( template );

        final StringWriter writer = new StringWriter();

        mustacheTemplate.execute( new Object()
        {
            List<Item> items = itemsIn.getItems();
        }, writer );

        return writer.toString();
    }

    public String create( final OrderSummary orderSummaryIn, final String template )
        throws Exception
    {
        Template mustacheTemplate = Mustache.compiler().escapeHTML( false ).compile( template );

        final StringWriter writer = new StringWriter();

        mustacheTemplate.execute( new Object()
        {
            OrderSummary orderSummary = orderSummaryIn;
        }, writer );

        return writer.toString();
    }


}
