package com.app.sportzfever.models.dbmodels.apimodel;

import java.util.List;

public class ExtraAndFOW
{
    private ExtraRuns extraRuns;
    private List<String> fow;
    public ExtraRuns getExtraRuns()
    {
        return extraRuns;
    }

    public void setExtraRuns(ExtraRuns extraRuns)
    {
        this.extraRuns = extraRuns;
    }

    public List<String> getFow()
    {
        return fow;
    }

    public void setFow(List<String> fow)
    {
        this.fow = fow;
    }

}
