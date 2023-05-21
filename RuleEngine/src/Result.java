public class Result
{
    private String comp_true;  // if evaluate is true
    private String comp_false; // if evaluate is false

    public String getComp_true() {
        return comp_true;
    }

    public void setComp_true(String comp_true) {
        this.comp_true = comp_true;
    }

    public String getComp_false() {
        return comp_false;
    }

    public void setComp_false(String comp_false) {
        this.comp_false = comp_false;
    }

    public Result()
    {
        this.comp_true = "";
        this.comp_false = "";
    }
}