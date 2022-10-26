package com;

public class Token
{


    public String token;
    public int state;




    public Token(int st, String tok) {

        token=tok;


       state=st;

    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Token guest = (Token) obj;
        return (state == guest.state )&& (token== guest.token || (token != null &&token.equals(guest.getToken())));
    }
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((token == null) ? 0 : token.hashCode());
        result = prime * result + state;

        return result;
    }

	public String getToken() {
		return token;
}

    public int getState() {
        return state;
    }



}
