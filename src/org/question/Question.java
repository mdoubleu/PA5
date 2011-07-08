package org.question;

import java.util.LinkedList;
import java.util.TreeMap;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnKeyListener;
import android.view.View.OnClickListener;

public class Question extends Activity implements OnClickListener{

	TextView textview;
	EditText edittext;
	View yes;
	View no;
	TreeMap<Integer, LinkedList<Animal>> map;
	int autoIncrement;
	int holdAuto;
	int count;
	boolean askedAnimal;
	boolean askedQuestion;
	boolean askedAnimalFromQuestion;
	String animal;
	String question;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
		yes=findViewById(R.id.yes);
		yes.setOnClickListener(this);
		no=findViewById(R.id.no);
		no.setOnClickListener(this);
	
		askedAnimal=false;
		askedQuestion=false;
		askedAnimalFromQuestion=false;
		map= new TreeMap<Integer, LinkedList<Animal>>();
		autoIncrement=0;
		holdAuto=0;
		count=0;
		animal="";
		question="";
		
		LinkedList<Animal> test= new LinkedList<Animal>();
		Animal first=new Animal("duckling", "Does it swim?");
		test.add(first);
		map.put(autoIncrement, test);
		
		textview=(TextView)this.findViewById(R.id.output);
		textview.setText("Greetings! Welcome to 20 questions, are you ready to begin (tap yes!):");
		edittext = (EditText) findViewById(R.id.edit);
		edittext.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                	if(animal.equals("")){
                		animal=edittext.getText().toString().toLowerCase();
                		textview.setText("What is true for a/n "+animal+" and false for a "+map.get(holdAuto).get(count).animal+"?");
                	}else{
                		question=edittext.getText().toString().toLowerCase();
                		if(!askedAnimalFromQuestion){//this checks if the new animal should be in some other list or a new key and list. 
                			autoIncrement++;
                			
                			map.put(autoIncrement, new LinkedList<Animal>());
    						map.get(autoIncrement).add(new Animal(animal, question));
                		}else{
                			edittext.setText("else"+autoIncrement);
                			map.get(holdAuto).add(new Animal(animal, question));
                		}
                		animal="";
                		question="";
                		textview.setText("Awesome, are you ready to play again?");
                		holdAuto=0;
                		count=-1;
                		askedAnimalFromQuestion=false;
                	}
                	
                  edittext.setText(""); 
                  return true;
                }
                return false;
            }
        });	
}
	public void onClick(View v){
		
		switch (v.getId())
		{
			case R.id.yes:
				textview.setText(yes());
				
				break;
			case R.id.no:
				textview.setText(no());
		        break;
		}
	}
	/*Returns the string to the textview if answer is yes*/
			public String yes(){
					if(askedAnimal||askedAnimalFromQuestion){
						holdAuto=0;
                		count=-1;
                		askedAnimalFromQuestion=false;
                		askedAnimal=false;
						return "YAY I WON! Ready to play again?";
					}
				
					if(map.get(holdAuto).size()>(count+1)){
						count++;
						askedQuestion=false;
					}
					if(!askedQuestion){
						askedQuestion=true;
						return map.get(holdAuto).get(count).question+"?";
					}
					askedAnimalFromQuestion=true;
					return "Is it a/n "+map.get(holdAuto).get(count).animal+"?";
							
			}
		/*Returns the string to the textview if answer is no*/
			public String no(){
				if(autoIncrement>holdAuto&&!askedAnimalFromQuestion&&!askedAnimal){
					holdAuto++;
					count=0;
					askedAnimal=false;
					return map.get(holdAuto).get(count).question;
				}
				else if(!askedAnimal&& !askedAnimalFromQuestion){
					askedAnimal=true;
					return "Is it a/n "+map.get(holdAuto).get(count).animal+"?";
					
				}
				else{		
					askedAnimal=false;
					return "Aw shucks, I lost, what was your animal?";
				}
				
			}
	}


