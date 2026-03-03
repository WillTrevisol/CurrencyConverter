package br.edu.ifsp.scl.sdm.currencyconverter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import br.edu.ifsp.scl.sdm.currencyconverter.R
import br.edu.ifsp.scl.sdm.currencyconverter.databinding.ActivityMainBinding
import br.edu.ifsp.scl.sdm.currencyconverter.model.livedata.CurrencyConverterLiveData
import br.edu.ifsp.scl.sdm.currencyconverter.ui.viewmodel.CurrencyConverterViewModel

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val currencyConverterViewModel: CurrencyConverterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        setSupportActionBar(amb.mainTb.apply { title = getString(R.string.app_name) })

        var fromQuote = ""
        var toQuote = ""
        val currenciesAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf<String>())

        with(amb) {
            fromQuoteMactv.apply {
                setAdapter(currenciesAdapter)
                onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
                    fromQuote = text.toString()
                }
            }

            toQuoteMactv.apply {
                setAdapter(currenciesAdapter)
                onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
                    toQuote = text.toString()
                }
            }

            convertBt.setOnClickListener {
                currencyConverterViewModel.convert(fromQuote, toQuote, amountTiet.text.toString())
            }
        }

        CurrencyConverterLiveData.currenciesLiveData.observe(this) { currencyList ->
            currenciesAdapter.clear()
            currenciesAdapter.addAll(currencyList.currencies.keys.sorted())
            currenciesAdapter.getItem(0)?.also { quote ->
                amb.fromQuoteMactv.setText(quote, false)
                fromQuote = quote
            }
            currenciesAdapter.getItem(currenciesAdapter.count - 1)?.also { quote ->
                amb.toQuoteMactv.setText(quote, false)
                toQuote = quote
            }
        }

        CurrencyConverterLiveData.conversionResultLiveData.observe(this) { conversionResult ->
            with(amb) {
                conversionResult.rates.values.first().rateForAmount.also {
                    resultTiet.setText(it)
                }
            }
        }

        currencyConverterViewModel.getCurrencies()
    }
}
