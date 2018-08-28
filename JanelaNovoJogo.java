import java.awt.*;
import java.awt.event.*;

class JanelaNovoJogo extends Frame implements ActionListener
{
	private Button ok;
	private CheckboxGroup c;
	private Choice tam;
	private Choice exp;
	private List init;
	private Checkbox _1on1;
	private Checkbox _1oncomp;
	private int auxTipo;
	private int auxTabTam;
	private double auxPerExp;
	private int auxPecasIn;
	
	public JanelaNovoJogo(CheckboxGroup c,Choice tam,Choice exp,List init,Checkbox _1on1,Checkbox _1oncomp,Button ok)
	{
		addActionListener(this);
		this.ok=ok;
		this._1on1=_1on1;
		this._1oncomp=_1oncomp;
		this.c=c;
		this.tam=tam;
		this.exp=exp;
		this.init=init;
		this.setTitle("Novo Jogo");
	}
	
	public void actionPerformed(ActionEvent e)
	{
	 	if((e.getSource()).equals(ok))
	 	{		
	 		Checkbox auxCh=c.getSelectedCheckbox();
	 		
	 		if(auxCh.equals(_1on1))
	 			auxTipo=0;
	 		else
		 		if(auxCh.equals(_1oncomp))
			 		auxTipo=2;
			
			switch(tam.getSelectedIndex())
	 		{
	 			case(0):auxTabTam=5;break;
	 			case(1):auxTabTam=6;break;
	 			case(2):auxTabTam=7;break;
	 			case(3):auxTabTam=8;break;
	 			case(4):auxTabTam=9;break;
	 		}
	 		
	 		switch(exp.getSelectedIndex())
	 		{
	 			case(0):auxPerExp=0.05;break;
	 			case(1):auxPerExp=0.10;break;
	 			case(2):auxPerExp=0.20;break;
	 			case(3):auxPerExp=0.30;break;
	 		}
	 		
	 		switch(init.getSelectedIndex())
	 		{
	 			case(0):auxPecasIn=2;break;
	 			case(1):auxPecasIn=3;break;
	 			case(2):auxPecasIn=4;break;
	 			case(3):auxPecasIn=5;break;
	 		}
	 		this.setVisible(false);
	 	 }
	 }
	 public double getPercentagem()
	 {
	 	return(auxPerExp);
	 }
	 
	 public int getNumPecas()
	 {
	 	return(auxPecasIn);
	 }
	 
	 public int getTipo()
	 {
	 	return(auxTipo);
	 }
	 
	 public int getTam()
	 {
	 	return(auxTabTam);
	 }
}