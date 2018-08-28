import java.io.*;

class Quad implements Serializable
{

	public final static boolean EMPTY=true;

  

  public final static byte UP    = 0 ;

  public final static byte RIGHT = 1 ;

  public final static byte DOWN  = 2 ;

  public final static byte LEFT  = 3 ;	

  private final static byte	DEF  = -1;//Valor atribuido por Default às partes do Quad

  

  public final static byte NPARTES = 4;	

  

	

	private byte [] parte=new byte [NPARTES];

	private boolean empty=false;

	private boolean explosiva=false;



	public Quad (Quad q)
	{
		for(byte c=UP;c<=LEFT;c++)
			this.parte[c] = q.getParte(c);

		empty = q.isEmpty();		

		explosiva=q.isExplosiva();

	}

	public Quad (int nHips)//quad aleatorio
	{
		empty=false;

		for (byte c=UP;c<=LEFT;c++) parte[c]=(byte)(Math.random()*nHips);

	}
	
	public Quad ()
	{

		empty=true;

		explosiva=false;

		for (byte c=UP;c<=LEFT;c++) parte[c]=DEF;//por default		
	}
	
	public Quad (byte [] parte)
	{

		empty=false;

		this.parte=parte;

	}

	public void resetQuad()
	{
		empty=true;
		for(int cnt=UP;cnt<=LEFT;cnt++)
			parte[cnt]=DEF;
	}

	// Avalia se dois quads são iguais numa posição
	// em termos de formato e não vazio ou explosivo
	public boolean eIgual(Quad q)
	{
		boolean res=false;

		for(int cnt=UP;cnt<=LEFT;cnt++)
		{
			res=res || (this.parte[UP]==q.parte[UP] && this.parte[RIGHT]==q.parte[RIGHT] && this.parte[DOWN]==q.parte[DOWN] && this.parte[LEFT]==q.parte[LEFT]);
			q.shiftRight();
		}
		
		return(res);
	}

  public void setEmpty(boolean empty)

  {

  	this.empty=empty;

  	if(empty==true)

  		for (byte c=UP;c<=LEFT;c++)parte[c]=DEF;

  }

	

  public void shiftRight()

  {

		byte [] newParte = new byte [NPARTES];

		System.arraycopy(parte,UP,newParte,RIGHT,LEFT);

		newParte[UP]=parte[LEFT];

		parte=newParte;	

  }

  public void shiftLeft()//Tem de ser manual pois com o Arraycopy torna-se complicado

  {

		byte [] newParte = new byte [NPARTES];

		newParte[RIGHT]=parte[DOWN];

		newParte[UP]=parte[RIGHT];

		newParte[LEFT]=parte[UP];

		newParte[DOWN]=parte[LEFT];

		parte=newParte;  	

  }



	public byte getUp()   {return parte[UP   ];}

	public byte getRight(){return parte[RIGHT];}

	public byte getDown() {return parte[DOWN ];}

	public byte getLeft() {return parte[LEFT ];}

	

	public boolean isExplosiva()

	{

		return(explosiva);

	}

	

	public void setExplosiva(boolean exp)

	{

		this.explosiva=exp;

	}

		

	public boolean isEmpty(){return empty;}

	

	public byte getParte(byte part){return parte[part];}

	

	public String toString()

	{

		return

					(

					" Cima :"    +parte[UP   ]+

					" Direita :" +parte[RIGHT]+

					" Baixo :"   +parte[DOWN ]+

					" Esquerda :"+parte[LEFT ]+". "

				+ "Vazio ="	+empty

				+ " Explosiva="+explosiva

					);

	}

		

}