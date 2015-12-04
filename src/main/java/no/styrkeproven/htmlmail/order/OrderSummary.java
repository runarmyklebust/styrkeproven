package no.styrkeproven.htmlmail.order;

import java.util.List;

import no.styrkeproven.htmlmail.item.Item;

public class OrderSummary
{
    private List<Item> items;

    private double sum;

    private int numberOfProducts;

    private OrderSummary( Builder builder )
    {
        items = builder.items;
        sum = builder.sum;
        numberOfProducts = builder.numberOfProducts;
    }

    public static Builder create()
    {
        return new Builder();
    }

    public List<Item> getItems()
    {
        return items;
    }

    public double getSum()
    {
        return sum;
    }

    public int getNumberOfProducts()
    {
        return numberOfProducts;
    }

    public static final class Builder
    {
        private List<Item> items;

        private double sum;

        private int numberOfProducts;

        private Builder()
        {
        }

        public Builder items( List<Item> items )
        {
            this.items = items;
            return this;
        }

        public Builder sum( double sum )
        {
            this.sum = sum;
            return this;
        }

        public Builder numberOfProducts( int numberOfProducts )
        {
            this.numberOfProducts = numberOfProducts;
            return this;
        }

        public OrderSummary build()
        {
            return new OrderSummary( this );
        }
    }
}
