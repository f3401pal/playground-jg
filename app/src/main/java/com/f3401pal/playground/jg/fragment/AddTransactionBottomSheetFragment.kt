package com.f3401pal.playground.jg.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.f3401pal.playground.jg.R
import com.f3401pal.playground.jg.databinding.FragmentAddTransactionBinding
import com.f3401pal.playground.jg.model.AddTransactionViewModel
import com.f3401pal.playground.jg.model.Expense
import com.f3401pal.playground.jg.model.Income
import com.f3401pal.playground.jg.model.TransactionType
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTransactionBottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel: AddTransactionViewModel by viewModels()
    private lateinit var viewBinding: FragmentAddTransactionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_transaction, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.viewModel = viewModel

        var type: TransactionType = Income
        viewBinding.typeChipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            when(checkedIds.first()) {
                R.id.incomeChip -> type = Income
                R.id.expenseChip -> type = Expense
            }
        }

        // clear errors when value changed
        viewBinding.description.editText?.addTextChangedListener {
            viewBinding.description.error = null
        }
        viewBinding.amount.editText?.addTextChangedListener {
            viewBinding.amount.error = null
        }

        viewBinding.actionSave.setOnClickListener {
            val description = viewBinding.description.editText?.text?.toString()
            val amount = viewBinding.amount.editText?.text?.toString()
            if(validateInputs(description, amount)) {
                viewModel.saveTransaction(type, description.orEmpty(), amount?.toFloat() ?: 0f)
                dismiss()
            }
        }
    }

    private fun validateInputs(description: String?, amount: String?): Boolean {
        var valid = true
        // description cannot be null or empty
        if(description.isNullOrEmpty()) {
            viewBinding.description.error = getString(R.string.error_empty_description)
            valid = false
        }
        if(amount.isNullOrEmpty()) {
            viewBinding.amount.error = getString(R.string.error_zero_amount)
            valid = false
        } else if(!amount.isDigitsOnly()) {
            viewBinding.amount.error = getString(R.string.error_not_digit)
            valid = false
        }

        return valid
    }

    companion object {
        const val TAG = "AddTransactionBottomSheetFragment"
    }
}