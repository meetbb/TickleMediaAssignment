# TickleMediaAssignment
This assignment is developed to submit in TickleMedia pvt. ltd.
For UI
1. At first, ui component is build using MainActivity file.
2. Add Framelayout in activity_main xml file.
3. In onCreate function attach the fragment named RepositoryListFragment to current activity.
4. Add RecyclerView in Fragment layout and attach it to adapter.
5. Create an object of ViewModel class from ViewModelsProvider.
6. Observe the liveData object of ViewModel and make API call to the server.

For Networking
1. add dependencies of retrofit, recyclerview, okhttp and Glide.
2. Create separate directory for repository and networking from API.
3. Create interface with input fuction and parameters in annotations. Pass this interface to retrofit method as interceptor.
4. Create one variable of MutableLiveData and assign the API response body to this LiveData variable.
5. Now the observer observing this LiveData in ViewModel will be notified once it receives any response from API.

ViewModels
1. Create a class and extend it with ViewModel.
2. This ViewModel class will retain the data according to the lifecycle state of the UI component.
3. Create a MutableLiveData variable and Observe it in Fragment class.
4. Create one function which will take input from UI component and pass the parameters to repository.
5. Process the response that we received from repository and assign it to the LiveData Object.

Adapter
1. Attach the recyclerview to the RecyclerAdapter and pass the array containing the data.
2. Once the array is updated the notifyDataSetChanged() function is called in adapter.
3. The recyclerview items will be iterated and its design in developed in separate internal class which extends Recyclerview.ViewHolder.
4. Glide is used to access the user images from the URL. It is more convenient and better to cache the images and achieve seemless scrolling of recyclerview.

Description Screen
1. When user clicks any particular user for its details, he will be redirected to another fragment.
2. This fragment has all logic of data binding. The parent tag of XML file is layout which allows us to attach the model data class which can be accessed in XML file.
3. Each text view and ImageView is accessing the property of data classc and to load image in Imageview and to process text data develop separate bindingadapter class.
4. Annotate the function with @BindingAdapter annotation which notifies the Android that the particular function is used while binding the data.
5. Now observe the LiveData in ViewModel the same way and reiterate the data accordingly from GITHUB Description API.

Lazy Loading
1. Create one separate layout with progress bar in it.
2. override one more function in Recyclerview adapter named viewType.
3. Add ScrollListener to Recyclerview and when user scrolls to the bottom add null value to the existing recyclerview array.
4. Check when the received item is null then show loading image view in recyclerview viewholder.
5. Once the data is received, remove previously added null data and attach new data to existing list.

Maintaining separate directories and less dependencies on upper layer of application. The code is loosely coupled and functions with less errors.
The MVVM architecture is followed and proper use of libraries is done.
