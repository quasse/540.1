/**
 * A k-means clustering algorithm implementation.
 * 
 */

public class KMeans {

	//Distortion initially set to an arbitrary value.
	private double distortion = 9999;

	public KMeansResult cluster(double[][] centroids, double[][] instances,
			double threshold) {
		/* ... YOUR CODE GOES HERE ... */

		//changes the centroid location until the threshold is reached.
		while(distortion > threshold){
			centroids = assignCentroids(centroids,instances);
		}
		return null;
	}

	/*
	 * Reassigns all points to a new centroid and calls the moveCentroids 
	 * function
	 */	private double[][] assignCentroids(double[][] centroids, 
			 double[][] instances){

		 //Each row represents a centroid.
		 //Each column represents a row in instances[][].
		 double[][] assignments = new double[centroids.length][instances.length];

		 //Array for holding the respective distances from an instance to a 
		 //centroid.
		 double[][] distances = new double[centroids.length][1];

		 //Array for holding the difference between a centroid and an instance.
		 double[][]differences = new double[centroids.length][instances[0].length];

		 /*
		  * Iterates through each instance, storing the difference between an 
		  * instance's feature location and the centroid's feature location
		  * in an array, differences[][]. Then, the method getClosest() returns
		  * whichever centroid is closest, and that centroid is assigned the 
		  * instance.
		  */
		 for (int i = 0; i < instances.length; i++){
			 for(int j = 0; j < centroids.length; j++){
				 for (int k = 0; k < centroids[j].length; k++){
					 /*
					  * Because it is a given that each instances[][] is as long
					  * as centroids[][], we can use the same variable to
					  * maneuver "horizontally" along each array.
					  */
					 differences[j][k] = centroids[j][k] - instances[i][k];
				 }//end for
			 }//end for

			 //Iterates through the assignments array, placing the assigned
			 //instance in the first empty space of whatever row the centroid 
			 //corresponds to.
			 int centroidAssignment = getClosest(differences);
			 for (int l = 0; l < assignments[centroidAssignment].length; l++){
				 if (assignments[centroidAssignment][l] == 0){
					 assignments[centroidAssignment][l] = i;
				 }
			 }
		 }//end for
		 findOrphans(assignments, centroids, instances);
		 moveCentroids(centroids, assignments);
		 return centroids;
	 }//end method

	 /*
	  * Moves all the centroids according to their new assignments and finds the 
	  * distortion for all the centroid movements.
	  */
	 private double[][] moveCentroids(double[][] centroids,
			 double[][] assignments){

		 return centroids;
	 }

	 /*
	  * Returns whatever centroid is closest to the instance.
	  * The centroid is indicated by its row.
	  */
	 private int getClosest(double[][] differences){

		 //Array to store the distances between each centroid and instance
		 double[] totalDistance = new double[differences.length];

		 //Will indicate which element of totalDistance is the smallest
		 int indicator = 0;

		 //Iterates through differences[][], adding each difference to the next
		 for (int i = 0; i < differences.length; i++){
			 for (int j = 0; j < differences[i].length; j++){
				 totalDistance[i] = totalDistance[i] + differences[i][j];
			 }
		 }


		 double min = totalDistance[0];

		 //Sees which element in totalDistance is the smallest
		 for (int i = 1; i < totalDistance.length; i++){
			 if (totalDistance[i] < min ){
				 indicator = i;
			 }//end if
		 }//end for
		 return indicator;
	 }//end method

	 /*
	  * Searhces through the assignments array and looks to see if any of the 
	  * rows are empty.
	  */
	 private void findOrphans(double assignments[][], double centroids[][],
			 double instances[][]){

		 boolean hasOrphans = false;
		 int orphanedCentroid = 0;
		 double distance = 0;
		 double max = 0;
		 double instance = 0;


		 for (int i = 0; i < assignments.length; i ++){
			 if (assignments[i][0] == 0){
				 hasOrphans = true;
				 orphanedCentroid = i;
			 }//end if 
		 }//end for

		 if (hasOrphans){
			 
			 //Searches through each instance, comparing the length between it
			 //and the centroid
			 for (int i = 0; i < instances.length; i++){
				 for (int j = 0; j < instances[i].length; j++){
					 distance = centroids[orphanedCentroid][j] - instances[i][j];
				 }//end for
				 
				 //checks if the distance between this instance and the centroid
				 //is the greatest
				 if (distance > max){
					 max = distance;
					 instance = i;
				 }//end if
				 
				 //resets the distance
				 distance = 0;
			 }//end for

			 //Assigns the instance to the Centroid.
			 assignments[orphanedCentroid][0] = instance;
			 
			 //Somehow need to get rid of the instance in the centroid it 
			 //used to be in.
		 }//end if

	 }//end findOrphans
}
