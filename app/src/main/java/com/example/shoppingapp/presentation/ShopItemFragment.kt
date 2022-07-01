package com.example.shoppingapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppingapp.R
import com.example.shoppingapp.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout
import java.lang.RuntimeException

class ShopItemFragment : Fragment() {

    private lateinit var shopItemViewModel: ShopItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var saveButton: Button

    private var shopItemId: Int = ShopItem.UNDEFINED_ID
    private var screenMode: String = SCREEN_MODE_UNDEFINED

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = requireArguments()

        if (!args.containsKey(EXTRA_MODE)) {
            throw RuntimeException("Parameter EXTRA_MODE is missing.")
        }

        screenMode = args.getString(EXTRA_MODE).toString()
        if (screenMode == MODE_EDIT) {
            shopItemId = args.getInt(EXTRA_MODE_EDIT_ID)
            if (!args.containsKey(EXTRA_MODE_EDIT_ID))
                throw RuntimeException("Parameter EXTRA_MODE_EDIT_ID is missing.")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        shopItemViewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]

        shopItemViewModel.closeScreen.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }

        when(screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }

        shopItemViewModel.errorInputName.observe(viewLifecycleOwner) {
            tilName.error = if (it) {
                resources.getString(R.string.til_name_error)
            } else {
                null
            }
        }

        shopItemViewModel.errorInputCount.observe(viewLifecycleOwner) {
            tilCount.error = if (it) {
                resources.getString(R.string.til_count_error)
            } else {
                null
            }
        }
    }

    private fun launchAddMode() {
        saveButton.setOnClickListener {
            shopItemViewModel.addItem(etName.text.toString(), etCount.text.toString())
        }
    }

    private fun launchEditMode() {
        shopItemViewModel.getItem(shopItemId)
        shopItemViewModel.shopItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        saveButton.setOnClickListener {
            shopItemViewModel.editItemUseCase(etName.text.toString(), etCount.text.toString())
        }
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        saveButton = view.findViewById(R.id.save_btn)
    }

    companion object {
        private const val EXTRA_MODE = "EXTRA_MODE"
        private const val MODE_ADD = "MODE_ADD"
        private const val MODE_EDIT = "MODE_EDIT"
        private const val EXTRA_MODE_EDIT_ID = "EXTRA_MODE_EDIT_ID"
        private const val SCREEN_MODE_UNDEFINED = "SCREEN_MODE_UNDEFINED"

        fun newInstanceAddShopItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditShopItem(id: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_MODE, MODE_EDIT)
                    putInt(EXTRA_MODE_EDIT_ID, id)
                }
            }
        }
    }
}