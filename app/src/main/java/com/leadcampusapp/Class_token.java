package com.leadcampusapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// "PaytmChecksum": "EkdqSgtcbVT5bw4dkPZtFnnvr/AHeaOiDgFG4DoFzBYz3UdiNs4NM9MBxbu65kCUEOnRXcvwRP9e0hNYtFJGpuvr1bOJlM4YTEQAhkaevHI=",
//        "VerifySignature": true,
public class Class_token
{

    @SerializedName("PaytmChecksum")
    @Expose
    private String PaytmChecksum;

    @SerializedName("VerifySignature")
    @Expose
    private String VerifySignature;


    @SerializedName("PaytmChecksumNew")
    @Expose
    private String PaytmChecksumNew;

    @SerializedName("VerifySignatureNew")
    @Expose
    private String VerifySignatureNew;


    public String getPaytmChecksum() {
        return PaytmChecksum;
    }

    public void setPaytmChecksum(String paytmChecksum) {
        PaytmChecksum = paytmChecksum;
    }

    public String getVerifySignature() {
        return VerifySignature;
    }

    public void setVerifySignature(String verifySignature) {
        VerifySignature = verifySignature;
    }

    public String getPaytmChecksumNew() {
        return PaytmChecksumNew;
    }

    public void setPaytmChecksumNew(String paytmChecksumNew) {
        PaytmChecksumNew = paytmChecksumNew;
    }

    public String getVerifySignatureNew() {
        return VerifySignatureNew;
    }

    public void setVerifySignatureNew(String verifySignatureNew) {
        VerifySignatureNew = verifySignatureNew;
    }
}
