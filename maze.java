class stackcell{
	int x, y, size;
	stackcell tail; //first
	public stackcell(int a, int b, int s, stackcell t){
		y=a; x=b; tail=t; size = s;
	}
	
	//comparison
	public boolean equals(stackcell target){
		return x==target.x && y==target.y;
	}
	
	//cut function
	public void cut(){
		if(size<2){return;}
		stackcell candidate = null;
		int count = 0, add = 0;
		
		stackcell i = tail;		
		while(i!=null){
			if(i.equals(tail)){
				candidate = i; count = add;
			}
			i = i.tail; add++;
		}if(candidate != null){tail = candidate;
			size = size - count;
		}
	}//stack class
}

class maze extends mazedfs
{	
	public maze(int bh0, int mh0, int mw0) // don't change constructor
	{
		super(bh0,mh0,mw0); 
	}

	public void customize(){
//		showvalue = true;
	}

	public void digout(int y, int x)   // modify this function
	{
		// The following is a skeleton program that demonstrates the mechanics
		// needed for the completion of the program.

		// We always dig out two spaces at a time: we look two spaces ahead
		// in the direction we're trying to dig out, and if that space has
		// not already been dug out, we dig out that space as well as the
		// intermediate space.  This makes sure that there's always a wall
		// separating adjacent corridors.

		M[y][x] = 1;  // digout maze at coordinate y,x
		drawblock(y,x);  // change graphical display to reflect space dug out
		delay(50); // slows animation

		//Permutation of Directions
		int[] P = {0,1,2,3};
		for(int i=0; i<P.length; i++){
			int r = i+(int)(Math.random()*P.length-i); //r is between i and P.length-1
			int temp = P[i]; //swap each element with some random element
			P[i] = P[r];
			P[r] = temp;
		}

		//NESW -> 0,1,2,3
		int []dX = {0, 1, 0, -1};
		int []dY = {-1, 0, 1, 0};

		for(int dir=0; dir<4; dir++){
			int rand=P[dir];
			int dx = dX[rand], dy = dY[rand];
			int nx = x+dx*2, ny = y+dy*2;

			if (nx>=0 && nx<mw && ny>=0 && ny<mh && M[ny][nx]==0) // always check for maze boundaries //order matters 
			{
				M[y+dy][x+dx] = 1;
				drawblock(y+dy,x+dx);
				digout(ny,nx);
			}
		}//done!
	}//digout

	public void trace(){
		
		while(stack != null){
			delay(50);
			drawdot(stack.y, stack.x);
			stack = stack.tail;
		}
	}

	stackcell stack = null;	
	int size = 0;

	public void solve()
	{
		int x=1, y=1;
		drawdot(1,1); //Start
		int a,b,c,d;
		int min = 9999, minIdx = -1;
		size++;
		stack = new stackcell(y,x,size,stack); 
		while(y!=mh-2 || x!=mw-1){ //While we haven't reached the goal.	
			delay(50);
			//Check if not a wall and value exists
			if(x+1>=0 && M[y][x+1]!=0){ a = M[y][x+1];}//right
			else{a = 127;}
		
			if(x-1>=0 && M[y][x-1]!=0){ b = M[y][x-1];}//left
			else{b = 127;}
	
			if(y+1>=0 && M[y+1][x]!=0){ c = M[y+1][x];}//up
			else{c = 127;}
	
			if(y-1>=0 && M[y-1][x]!=0){ d = M[y-1][x];}//down
			else{d = 127;}
			

			// Find the smallest value
			min = a;
			minIdx = 0;
			if(b>0 && min>b){min = b; minIdx = 1;}
			if(c>0 && min>c){min = c; minIdx = 2;}
			if(d>0 && min>d){min = d; minIdx = 3;}	
			
			//drawblock(x,y);//erase current location
			if(minIdx==0){//Move through the maze
				drawblock(y,x);// erase previous location
				drawdot(y,x+1);// redefine x and y accordingly
				M[y][x+1]++; // Update value
				size++;
				stack = new stackcell(y,x+1,size,stack);
				stack.cut();
				x++;// increment M

			}else if(minIdx==1){
				drawblock(y,x);
				drawdot(y,x-1);
				M[y][x-1]++; // Update value
				size++;
				stack = new stackcell(y,x-1,size,stack);
				stack.cut();
				x--;
				
			}else if(minIdx==2){
				drawblock(y,x);
				drawdot(y+1,x);
				M[y+1][x]++; // Update value
				size++;
				stack = new stackcell(y+1,x,size,stack);
				stack.cut();
				y++;
				
			}else{
				drawblock(y,x);
				drawdot(y-1,x);
				M[y-1][x]++; // Update value
				size++;
				stack = new stackcell(y-1,x,size,stack);
				stack.cut();
				y--;				

			}
		}
		
	}//solve

}//studentcode subclass

