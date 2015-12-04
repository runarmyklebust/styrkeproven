package no.styrkeproven.htmlmail.content;

public class InputTypeDef
{
    public final String inputType;

    public final String name;


    public InputTypeDef( final String inputType, final String name )
    {
        this.inputType = inputType;
        this.name = name;
    }

    public String getInputType()
    {
        return inputType;
    }

    public String getName()
    {
        return name;
    }
}
