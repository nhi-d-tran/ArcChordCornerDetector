import java.io.BufferedWriter;
import java.io.IOException;

public class image
{
	int numRows;
	int numCols;
	int minVal;
	int maxVal;
	int[][] img;
	
	image(int row, int col, int min, int max)
	{
		numRows = row;
		numCols = col;
		minVal = min;
		maxVal = max;
		img = new int[numRows][numCols];
		
		for(int i = 0; i < numRows; i++)
		{
			for(int j = 0; j < numCols; j++)
			{
				img[i][j] = 0;
			}
		}
	}

	void plotPt2Img(boundaryPt[] pt, int numPts)
	{
		for(int i = 0; i < numPts; i++)
		{
			int r = pt[i].x;
			int c = pt[i].y;
			img[r][c] = pt[i].corner;
	    }
	}
	
	void prettyPrint(BufferedWriter outFile)
	{
		try 
		{
			for(int i = 0; i < numRows; i++)
			{
				for(int j = 0; j < numCols; j++)
				{
					if(img[i][j] > 0)
					{
						outFile.write(img[i][j]+ " ");
					}
					else
					{
						outFile.write(" ");
					}
				}
				outFile.write("\n");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
