package com.mobdeve.s11.mco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.mobdeve.s11.mco.databinding.FragmentMenuItemBinding

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
        val IMAGE = "image_key"
    }

    private var _binding: FragmentMenuItemBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var titleString: String
    private lateinit var priceString: String
    private lateinit var imageId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            titleString = it.getString(TITLE).toString()
            priceString = it.getString(PRICE).toString()
            imageId = it.getString(IMAGE).toString()
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

        val titleView = view.findViewById<TextView>(R.id.menu_fragment_title)
        val priceView = view.findViewById<TextView>(R.id.menu_fragment_price)
        val descView = view.findViewById<TextView>(R.id.menu_fragment_description)
        val imageView = view.findViewById<ImageView>(R.id.menu_fragment_image)
        val cartButton = view.findViewById<MaterialButton>(R.id.menu_fragment_addToCart)

        titleView.text = titleString
        priceView.text = priceString

        cartButton.setOnClickListener{

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