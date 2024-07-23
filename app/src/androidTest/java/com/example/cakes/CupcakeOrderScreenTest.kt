package com.example.cakes

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.cakes.data.DataSource
import com.example.cakes.data.OrderUiState
import com.example.cakes.ui.OrderSummaryScreen
import com.example.cakes.ui.SelectOptionScreen
import com.example.cakes.ui.StartOrderScreen
import org.junit.Rule
import org.junit.Test

class CupcakeOrderScreenTest {
    /**
     * Note: To access to an empty activity, the code uses ComponentActivity instead of
     * MainActivity.
     */
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeOrderUiTest = OrderUiState(
        quantity = 6,
        flavor = "Vanilla",
        date = "Wed Jul 11",
        price = "$150",
        pickupOptions = listOf()
    )

    /**
     * When quantity options are provided to StartOrderScreen, the options are displayed on the
     * screen.
     */

    @Test
    fun startOrderScreen_verifyContent() {
        //start order screen is loaded
        composeTestRule.setContent { 
            StartOrderScreen(
                quantityOption = DataSource.quantityOptions,
                onNextButtonClicked = {}
            )
        }

        //then all options are displayed on the screen
        DataSource.quantityOptions.forEach {
            composeTestRule.onNodeWithStringId(it.first).assertIsDisplayed()
        }

    }

    @Test
    fun selectOptionScreen_verifyContent(){
        //given a list of flavours
        val flavours = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")
        //add subtotal
        val subtotal = "$150"

        composeTestRule.setContent {
            SelectOptionScreen(subtotal = subtotal, options = flavours)
        }
        //Then all flavour are displayed on the screen
        flavours.forEach{ flavour->
            composeTestRule.onNodeWithText(flavour).assertIsDisplayed()
        }

        //And then subtotal is displayed correctly
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.subtotal_price, subtotal)).assertIsDisplayed()

        //And then next button is disabled
        composeTestRule.onNodeWithStringId(R.string.next).assertIsNotEnabled()
    }

    @Test
    fun summaryScreen_verifyContent() {
        //summary screen is loaded
        composeTestRule.setContent {
            OrderSummaryScreen(
                orderUiState = fakeOrderUiTest ,
                onCancelButtonClicked = {  },
                onSendButtonClicked = {_,_ ->}
            )
        }

        //then all options are displayed on the screen
        composeTestRule.onNodeWithText(fakeOrderUiTest.flavor).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeOrderUiTest.date).assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.subtotal_price, fakeOrderUiTest.price)).assertIsDisplayed()
    }

}