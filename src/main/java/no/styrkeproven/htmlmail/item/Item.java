package no.styrkeproven.htmlmail.item;

public class Item
{
    private final String displayName;

    private final int amount;

    private final int price;

    private final String size;

    private Item( Builder builder )
    {
        displayName = builder.displayName;
        amount = builder.amount;
        price = builder.price;
        size = builder.size;
    }

    public String toHtml()
    {

        return "<li>" + this.displayName + "</li>";
    }

    @Override
    public String toString()
    {
        return "Item{" +
            "displayName='" + displayName + '\'' +
            ", amount=" + amount +
            ", price=" + price +
            ", size='" + size + '\'' +
            '}';
    }

    public static Builder create()
    {
        return new Builder();
    }

    public boolean isBought()
    {
        return amount > 0;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public int getAmount()
    {
        return amount;
    }

    public int getPrice()
    {
        return price;
    }

    public String getSize()
    {
        return size;
    }

    public int getTotal()
    {
        return amount * price;
    }

    public static final class Builder
    {
        private String displayName;

        private int amount;

        private int price;

        private String size;

        private Builder()
        {
        }

        public Builder displayName( String displayName )
        {
            this.displayName = displayName;
            return this;
        }

        public Builder amount( int amount )
        {
            this.amount = amount;
            return this;
        }

        public Builder price( int price )
        {
            this.price = price;
            return this;
        }

        public Builder size( String size )
        {
            this.size = size;
            return this;
        }

        public Item build()
        {
            return new Item( this );
        }
    }
}
