package com.mobdeve.s11.mco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.mobdeve.s11.mco.data.CartData
import com.mobdeve.s11.mco.databinding.FragmentMenuItemBinding
import com.mobdeve.s11.mco.model.Cart

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MenuItem.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuItem : Fragment() {
    // TODO: Rename and change types of parameters

    companion object {
        val TITLE = "title_key"
        val PRICE = "price_key"
        val MAXPRICE = "maxPrice_key"
        val IMAGE = "image_key"
        val DESCRIPTION = "description_key"
        val QUANTITY = "quantity_key"
        val SIZE = "size_key"

    }

    private var _binding: FragmentMenuItemBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var titleString: String
    private lateinit var priceString: String
    private lateinit var maxPriceString: String
    private lateinit var imageId: String
    private lateinit var description: String
    private lateinit var quantityString: String
    private lateinit var sizeString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            titleString = it.getString(TITLE).toString()
            priceString = it.getString(PRICE).toString()
            maxPriceString = it.getString(MAXPRICE).toString()
            imageId = it.getString(IMAGE).toString()
            description = it.getString(DESCRIPTION).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMenuItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val coffeeImages = intArrayOf(
            R.drawable.papua_new, R.drawable.wild_flower,
            R.drawable.grapos_decaf, R.drawable.golden_ticket,
            R.drawable.new_legazpi
        )

        val titleView = view.findViewById<TextView>(R.id.menu_fragment_title)
        val priceView = view.findViewById<TextView>(R.id.menu_fragment_price)
        val descView = view.findViewById<TextView>(R.id.menu_fragment_description)
        val imageView = view.findViewById<ImageView>(R.id.menu_fragment_image)
        val cartButton = view.findViewById<MaterialButton>(R.id.menu_fragment_addToCart)
        val sizeRadio = view.findViewById<RadioGroup>(R.id.menu_fragment_size_radio)
        val quantityRadio = view.findViewById<RadioGroup>(R.id.menu_fragment_quantity_radio)
        val size250 = view.findViewById<RadioButton>(R.id.radio_button_250g)
        val size1kg = view.findViewById<RadioButton>(R.id.radio_button_1kg)
        val quantity1 = view.findViewById<RadioButton>(R.id.radio_button_1)
        val quantity2 = view.findViewById<RadioButton>(R.id.radio_button_2)
        val quantity3 = view.findViewById<RadioButton>(R.id.radio_button_3)
        val quantity4 = view.findViewById<RadioButton>(R.id.radio_button_4)
        val quantity5 = view.findViewById<RadioButton>(R.id.radio_button_5)
        var finalSize = ""
        var finalQuantity = 0
        var finalPrice = priceString
        val currImage = 1

        titleView.text = titleString
        priceView.text = "$priceString to $maxPriceString"
        descView.text =description
        imageView.setImageResource(requireContext().resources.getIdentifier(imageId, "drawable", requireContext().packageName))


        cartButton.setOnClickListener{
            val sizeAns = sizeRadio.checkedRadioButtonId
            val quantityAns = quantityRadio.checkedRadioButtonId

            if(sizeAns!=-1 && quantityAns!=-1) {
                if (size250.isChecked()) {
                    finalSize = "Regular"
                    finalPrice = priceString
                } else {
                    finalSize = "Large"
                    finalPrice = maxPriceString
                }

                if (quantity1.isChecked()) {
                    finalQuantity = 1
                } else if (quantity2.isChecked()) {
                    finalQuantity = 2
                } else if (quantity3.isChecked()) {
                    finalQuantity = 3
                } else if (quantity4.isChecked()) {
                    finalQuantity = 4
                } else if (quantity5.isChecked()) {
                    finalQuantity = 5
                }


                val bundle = Bundle()
                bundle.putString("title_key", titleString)
                bundle.putString("price_key", finalPrice)
                bundle.putString("image_key", imageId)
                bundle.putString("quantity_key", finalQuantity.toString())
                bundle.putString("size_key", finalSize)
                bundle.putString("description_key", description)

                CartData.cartItems.add(Cart(imageId, titleString, priceString, finalSize, finalQuantity))
                // Navigate using that action
                view.findNavController().navigate(R.id.item_to_menu, bundle)
            }
            else {
                Toast.makeText(requireContext(), "Please fill in the blanks!",
                    Toast.LENGTH_SHORT).show();
            }
        }
        }

    /**
     * Frees the binding object when the Fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}