package no.styrkeproven.htmlmail.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Items
    implements Iterable<Item>
{
    final List<Item> items = new ArrayList<Item>();

    public Item get( final String name )
    {
        for ( final Item item : items )
        {
            if ( item.getDisplayName().equals( name ) )
            {
                return item;
            }
        }

        return null;
    }

    public int size()
    {
        return items.size();
    }

    public List<Item> getItems()
    {
        return items;
    }

    public void add( final Item item )
    {
        this.items.add( item );
    }

    public String toHtml()
    {
        StringBuilder builder = new StringBuilder();

        for ( final Item item : items )
        {
            builder.append( item.toHtml() );
        }

        return builder.toString();
    }

    @Override
    public Iterator<Item> iterator()
    {
        return items.iterator();
    }
}
