package com.coolreecedev.styledpractice

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.coolreecedev.styledpractice.data.Appointment
import com.coolreecedev.styledpractice.data.availabledate.AvailableDate
import com.coolreecedev.styledpractice.data.customer.Customer
import com.coolreecedev.styledpractice.ui.AvailableDateFragment
import com.coolreecedev.styledpractice.ui.ZipCodeFragment
import com.coolreecedev.styledpractice.ui.ZipCodeFragmentDirections
import com.coolreecedev.styledpractice.util.LOG_TAG
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.stripe.android.Stripe
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.android.synthetic.main.fragment_schedule_date.*
import kotlinx.android.synthetic.main.fragment_schedule_date.view.*
import java.io.File
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ActivateClientActivity : AppCompatActivity(),
    AvailableDateFragment.OnListFragmentInteractionListener,
    ZipCodeFragment.OnListFragmentInteractionListener,
    PaymentFragment.OnListFragmentInteractionListener {
    private val mListener: ZipCodeFragment.OnListFragmentInteractionListener? = null
    var RC_SIGN_IN = 1
    private lateinit var auth: FirebaseAuth
    private lateinit var stripe: Stripe
    private lateinit var mZipCode: String
    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    lateinit var storage: FirebaseStorage
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService




    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is ZipCodeFragment) {
            fragment.onAttach(this)
        } else if (fragment is FirstScreenFragment) {
            fragment.onAttach(this)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activate_client)
        stripe = Stripe(applicationContext, "pk_test_dGcgdptaqW6r4MnnqAZdktZ3")

        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()

        val checkout = intent.getBooleanExtra("checkout", false)
        val customer = intent.getParcelableExtra<Customer>("customer")
        val appointment = intent.getParcelableExtra<Appointment>("appointment")
        mZipCode = ""
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null && checkout) {
            val bundle = Bundle()
            bundle.putParcelable("appointment", appointment)
            bundle.putParcelable("customer", customer)

            bundle.putString("transaction_number", UUID.randomUUID().toString())
            findNavController(R.id.nav_host).navigate(R.id.checkoutFragment, bundle)
            Log.i(LOG_TAG, "ActivateClientActivity: appointmentId: ${appointment?.appointment_id}")
            Log.i(LOG_TAG, "ActivateClientActivity: customerUID: ${customer?.uid}")
            Toast.makeText(this, "Checkout time!", Toast.LENGTH_SHORT).show()
        }else if (user != null && !checkout) {
            findNavController(R.id.nav_host).navigate(R.id.appointmentLookupFragment)
        }else {
            findNavController(R.id.nav_host).navigate(R.id.firstScreenFragment)
        }

    }

    fun navigateTo(frag: Int) {
        findNavController(R.id.nav_host).navigate(frag)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser

                val uid = user!!.uid

                Log.i(LOG_TAG, "Navigating to Occasion Fragment")


                val action = ZipCodeFragmentDirections.actionZipCodeDestToOccasionFragment(zipCode = mZipCode,
                    customer = Customer(uid = uid, first_name = user.displayName, email = user.email, zip = mZipCode, image_url = "${uid}.jpg")
                )
                findNavController(R.id.nav_host).navigate(action)
                // ...
            } else {
                navigateTo(R.id.firstScreenFragment)
                Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show()
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }


    override fun onListFragmentInteraction(available: Boolean, zipCode: String?) {
        if (available && zipCode != null) {
            Toast.makeText(this, "Available", Toast.LENGTH_SHORT).show()
            mZipCode = zipCode

            // Choose authentication providers
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build())

            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                user.let {
                    // Name, email address, and profile photo Url
                    val name = user.displayName
                    val email = user.email
                    val photoUrl = user.photoUrl

                    // Check if user's email is verified
                    val emailVerified = user.isEmailVerified

                    // The user's ID, unique to the Firebase project. Do NOT use this value to
                    // authenticate with your backend server, if you have one. Use
                    // FirebaseUser.getToken() instead.
                    val uid = user.uid

                    Log.i(LOG_TAG, "Navigating to Occasion Fragment")

                    mZipCode = zipCode

                    val action = ZipCodeFragmentDirections.actionZipCodeDestToOccasionFragment(zipCode = zipCode,
                    customer = Customer(uid = uid, first_name = name, email = email, zip = zipCode, image_url = "${uid}.jpg")
                    )
                    findNavController(R.id.nav_host).navigate(action)
                }
            } else {
                // No user is signed in

                // Create and launch sign-in intent
                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.logo_black) // Set logo drawable
                        .setTheme(R.style.AppTheme) // Set theme
                        .build(),
                    RC_SIGN_IN)
            }

        } else {
            val bundle = Bundle()
            bundle.putString("zipCode", zipCode)
            bundle.putString("occasion", "")
            Toast.makeText(this, "UnAvailable", Toast.LENGTH_SHORT).show()
            findNavController(R.id.nav_host).navigate(R.id.invalidZipCodeFragment, bundle)
        }
    }


    override fun onListPaymentFragmentInteraction(success: Boolean) {
//        findNavController(R.id.nav_host).navigate(R.id.budgetFragment)
    }

    override fun onListFragmentInteraction(item: AvailableDate?) {
        Log.i(LOG_TAG, "Available Date: $item")
        findNavController(R.id.nav_host).navigate(R.id.action_appointmentFragment)
    }

    fun showHamburger(view: View) {
        findNavController(R.id.nav_host).navigate(R.id.drawNavFragment)
    }

    fun followOnInstagram(view: View) {
        openWebPage("https://instagram.com/styledbylovee?igshid=14u459howkdwj")
    }

    fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    fun followOnFacebook(view: View) {
        openWebPage("https://m.facebook.com/StyledbyLoveE/?tsid=0.9902003236330261&source=result")
    }

    fun openHowItWorks(view: View) {
        val intent = Intent(this, LearnMoreActivity::class.java)
        startActivity(intent)
    }

    fun openOurStory(view: View) {
        val intent = Intent(this, OurStoryActivity::class.java)
        startActivity(intent)
    }

    fun openBlog(view: View) {
        val intent = Intent(this, BlogActivity::class.java)
        startActivity(intent)
    }

    fun ourStylist(view: View) {
        val intent = Intent(this, StylistActivity::class.java)
        startActivity(intent)
    }

    fun openTestimonials(view: View) {
        val intent = Intent(this, TestimonalActivity::class.java)
        startActivity(intent)
    }

    fun openFaq(view: View) {
        val intent = Intent(this, FaqActivity::class.java)
        startActivity(intent)
    }

    fun exitNav(view: View) {
        val intent = Intent(this, ActivateClientActivity::class.java)
        startActivity(intent)
    }

    fun displayLearnMore(view: View) {
        val intent = Intent(this, LearnMoreActivity::class.java)
        startActivity(intent)
    }

    fun getStyled(view: View) {
        findNavController(R.id.nav_host).navigate(R.id.zip_code_dest)
    }


    fun chooseDate(view: View) {
        val newFragment: DialogFragment = DatePickerFragment()
        newFragment.show(supportFragmentManager, "datePicker")
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun chooseTime(view: View) {
        var timeSlotText = ""
        when (view) {
            view.fourSixButton-> {
                fourSixButton.background  = resources.getDrawable(R.color.colorAccent)
                elevenOneButton.background = resources.getDrawable(R.drawable.button_border)
                twoFourButton.background = resources.getDrawable(R.drawable.button_border)
                fiveSevenButton.background = resources.getDrawable(R.drawable.button_border)
                sixEightButton.background = resources.getDrawable(R.drawable.button_border)
                timeSlotText = fourSixButton.text.toString()
            }
            view.elevenOneButton->{
                elevenOneButton.setBackgroundColor(resources.getColor(R.color.colorAccent))
                fourSixButton.background = resources.getDrawable(R.drawable.button_border)
                twoFourButton.background = resources.getDrawable(R.drawable.button_border)
                fiveSevenButton.background = resources.getDrawable(R.drawable.button_border)
                sixEightButton.background = resources.getDrawable(R.drawable.button_border)
                timeSlotText = elevenOneButton.text.toString()
            }
            view.twoFourButton->{
                twoFourButton.setBackgroundColor(resources.getColor(R.color.colorAccent))
                elevenOneButton.background  = resources.getDrawable(R.drawable.button_border)
                fourSixButton.background = resources.getDrawable(R.drawable.button_border)
                fiveSevenButton.background = resources.getDrawable(R.drawable.button_border)
                sixEightButton.background = resources.getDrawable(R.drawable.button_border)
                timeSlotText = twoFourButton.text.toString()
            }
            view.fiveSevenButton->{
                fiveSevenButton.setBackgroundColor(resources.getColor(R.color.colorAccent))
                elevenOneButton.background  = resources.getDrawable(R.drawable.button_border)
                fourSixButton.background = resources.getDrawable(R.drawable.button_border)
                twoFourButton.background = resources.getDrawable(R.drawable.button_border)
                sixEightButton.background = resources.getDrawable(R.drawable.button_border)
                timeSlotText = fiveSevenButton.text.toString()
            }
            view.sixEightButton->{
                sixEightButton.background  = resources.getDrawable(R.color.colorAccent)
                timeSlotText = sixEightButton.text.toString()
            }
        }
        Toast.makeText(this, timeSlotText, Toast.LENGTH_SHORT).show()

    }

    fun choosePricingOption(view: View) {
        navigateTo(R.id.scheduleDateFragment)
    }


    fun confirmAppointment(view: View) {
        navigateTo(R.id.addressFragment)
    }

    fun selectBudget(view: View) {
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
    }


    fun selectRomperJumpsuit(view: View) {
        navigateTo(R.id.printPatternsFragment)
    }
    fun chooseDress(view: View) {
        navigateTo(R.id.romperJumperFragment)

    }
    fun choosePantsSkirt(view: View) {
        navigateTo(R.id.dressFragment)

    }
    fun chooseShirtsBlouse(view: View) {
        navigateTo(R.id.pantsSkirtFragment)

    }

    fun selectColors(view: View) {
        navigateTo(R.id.budgetFragment)
    }

    fun goToSettings(view: View) {
        navigateTo(R.id.accountSettingsFragment)
    }

    fun confirmAddress(view: View) {
        navigateTo(R.id.paymentFragment)
    }

    fun confirmDressSkirts(view: View) {
        navigateTo(R.id.womenPickClothingTwoFragment)
    }

    fun confirmShirtJumpsuit(view: View) {
        navigateTo(R.id.printPatternsFragment)
    }

    fun confirmBodyType(view: View) {
        navigateTo(R.id.womenPickClothingFragment)
    }

    fun editAccount(view: View) {
        navigateTo(R.id.editFragment)
    }

    fun editStyle(view: View) {
        navigateTo(R.id.editStyleFragment)
    }

    fun viewAccount(view: View) {
        navigateTo(R.id.appointmentLookupFragment)
    }

    fun navigatePreviousScreen(view: View) {
        findNavController(R.id.nav_host).navigateUp()
    }

    fun logOut(view: View) {
       FirebaseAuth.getInstance().signOut()
        navigateTo(R.id.firstScreenFragment)
        Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show()

    }

    fun viewAppointmentDetails(view: View) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val action = AppointmentLookupFragmentDirections.actionAppointmentLookupFragmentToCustomerAppointmentFragment(user.uid)
            findNavController(R.id.nav_host).navigate(action)
        }
    }

    fun scheduleAppointment(view: View) {
        navigateTo(R.id.zip_code_dest)
    }

    fun saveProduct(view: View) {
        navigateTo(R.id.cameraFragment)
//        val intent = Intent(this, CameraActivity::class.java)
//        startActivity(intent)
    }

    private fun startCamera(lenFacingFront: Boolean) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            preview = Preview.Builder()
                .build()

            imageCapture = ImageCapture.Builder()
                .build()

            val cameraSelector = if (lenFacingFront) {
                CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                    .build()
            }else {
                CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()
            }

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                camera = cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
                preview?.setSurfaceProvider(viewFinder.surfaceProvider)
            } catch (exc: Exception) {
                Log.e(LOG_TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera(false)
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val user = FirebaseAuth.getInstance().currentUser!!
        val imageCapture = imageCapture ?: return

        // Create timestamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            user.uid + ".jpg"
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Setup image capture listener which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(LOG_TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {

                    val savedUri = Uri.fromFile(photoFile)

                    if (user != null) {

                        val storageRef = storage.reference


                        val imagesRef: StorageReference? =
                            storageRef.child("customer/images/${savedUri.lastPathSegment}")


                        val uploadTask = imagesRef?.putFile(savedUri)
                        uploadTask?.addOnFailureListener {
                            // Handle unsuccessful uploads
                            Log.e(LOG_TAG, "Imaged failed to saved in FB", it)

                        }?.addOnSuccessListener { taskSnapshot ->
                            Log.i(LOG_TAG, "Imaged saved in FB")
                            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                            // ...
                        }
                    } else {
                        Log.i(LOG_TAG, "user is null")
                    }

                    val msg = "Photo capture succeeded: $savedUri"
                    Toast.makeText(baseContext, "Thank you", Toast.LENGTH_SHORT).show()
                    Toast.makeText(baseContext, "Sign up completed", Toast.LENGTH_SHORT).show()
                    Log.d(LOG_TAG, msg)
                }
            })
    }

    fun cameraCapturePhoto(view: View) {
        takePhoto()
    }


}


class DatePickerFragment : DialogFragment(),
    OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c[Calendar.YEAR]
        val month = c[Calendar.MONTH]
        val day = c[Calendar.DAY_OF_MONTH]


        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireActivity(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {

        val vargs = Bundle()
        vargs.putString("param2", "${month.inc()}/$day/$year")
        val c = Calendar.getInstance()
        c.set(year, month, day)
        when(c.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SATURDAY -> vargs.putString("param1", "Saturday")
            Calendar.SUNDAY -> vargs.putString("param1", "Sunday")
            else-> vargs.putString("param1", "Weekday ")
        }

        Log.i(LOG_TAG, "Date selected: ${month.inc()}/$day/$year")
//        findNavController().navigate(R.id.scheduleDateFragment, vargs)
    }


}



