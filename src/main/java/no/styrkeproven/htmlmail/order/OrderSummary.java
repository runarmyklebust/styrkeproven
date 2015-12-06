package no.styrkeproven.htmlmail.order;

import java.util.List;
import java.util.Map;

import no.styrkeproven.htmlmail.item.Item;

public class OrderSummary
{
    private final List<Item> items;

    private final int sum;

    private final int numberOfProducts;

    private final String email;

    private final String name;

    private final String address;

    private final String zipcode;

    private final String city;

    private OrderSummary( Builder builder )
    {
        items = builder.items;
        sum = builder.sum;
        numberOfProducts = builder.numberOfProducts;

        this.name = getParam( "Fornavn", builder.parameterMap ) + " " + getParam( "Etternavn", builder.parameterMap );
        this.address = getParam( "Adresse", builder.parameterMap );
        this.zipcode = getParam( "Postnr", builder.parameterMap );
        this.city = getParam( "By", builder.parameterMap );
        this.email = getParam( "cc", builder.parameterMap );
    }


    private String getParam( final String name, final Map<String, String[]> paramsMap )
    {
        if ( paramsMap.containsKey( name ) )
        {
            return paramsMap.get( name )[0];
        }

        return "UNKNOWN";
    }

    public static Builder create()
    {
        return new Builder();
    }

    public List<Item> getItems()
    {
        return items;
    }

    public int getSum()
    {
        return sum;
    }

    public int getNumberOfProducts()
    {
        return numberOfProducts;
    }

    public String getEmail()
    {
        return email;
    }

    public String getName()
    {
        return name;
    }

    public String getAddress()
    {
        return address;
    }

    public String getZipcode()
    {
        return zipcode;
    }

    public String getCity()
    {
        return city;
    }

    public static final class Builder
    {
        private List<Item> items;

        private int sum;

        private int numberOfProducts;

        private Map<String, String[]> parameterMap;

        private Builder()
        {
        }

        public Builder items( List<Item> items )
        {
            this.items = items;
            return this;
        }

        public Builder sum( int sum )
        {
            this.sum = sum;
            return this;
        }

        public Builder numberOfProducts( int numberOfProducts )
        {
            this.numberOfProducts = numberOfProducts;
            return this;
        }

        public Builder setParameterMap( final Map<String, String[]> parameterMap )
        {
            this.parameterMap = parameterMap;
            return this;
        }

        public OrderSummary build()
        {
            return new OrderSummary( this );
        }
    }
}
