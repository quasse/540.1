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
		 
		 double average;
		 double distance;

		 for (int i = 0; i < instances.length; i++){
			 for(int j = 0; j < centroids.length; j++){
				 for (int k = 0; k < centroids[j].length; k++){
					 differences[j][k] = centroids[j][k] - instances[i][k];
				 }
				getClosest(differences);
			 }
		 }
		 moveCentroids(centroids, assignments);
		 return centroids;
	 }

	 /*
	  * Moves all the centroids according to their new assignments and finds the 
	  * distortion for all the centroid movements.
	  */
	 private double[][] moveCentroids(double[][] centroids,
			 double[][] assignments){

		 return centroids;
	 }
	 
	 private double getClosest(double[][] differences){
		 
	 }
}
