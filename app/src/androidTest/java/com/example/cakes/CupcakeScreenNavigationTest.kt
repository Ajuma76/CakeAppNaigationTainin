package com.example.cakes

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CupcakeScreenNavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupCupcakeNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            CupcakeApp(navController = navController)
        }
    }

    @Test
    fun cupcakeNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }


    //verify that the Start screen does not have an up button.
    @Test
    fun cupcakeNavHost_verifyBackNavigationNotShownOnStartOrderScreen(){
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    //verify navigation to the flavour screen
    @Test
    fun cupcakeNavHost_clickOneCupcake_navigatesToSelectFlavourScreen(){
        composeTestRule.onNodeWithStringId(R.string.one_cupcake).performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Flavour.name)

    }

    //Navigating to the Start screen by clicking the Up button from the Flavor screen
    @Test
    fun cupcakeNavHost_clickUpButton_navigatesToStartOrderScreen(){
        navigateToFlavourScreen()
        performNavigationUp()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    //Navigating to the Start screen by clicking the Cancel button from the Flavor screen
    @Test
    fun cupcakeNavHost_clickCancelButtonOnFlavourScreen_navigatesToStartOrderScreen(){
        navigateToFlavourScreen()
        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    //Navigating to the Pickup screen
    @Test
    fun cupcakeNavHost_clickNextOnFlavourScreen_navigatesToPickUpScreen(){
        navigateToFlavourScreen()
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Pickup.name)
    }

    //Navigating to the Flavor screen by clicking the Up button from the Pickup screen
    @Test
    fun cupcakeNavHost_clickUpButtonOnPickupScreen_navigatesToFlavourScreen(){
        navigateToPickupScreen()
        performNavigationUp()
        navController.assertCurrentRouteName(CupcakeScreen.Flavour.name)
    }

    //Navigating to the Start screen by clicking the Cancel button from the Pickup screen
    @Test
    fun cupcakeNavHost_clickCancelButtonOnPickUpScreen_navigatesToStartScreen(){
        navigateToPickupScreen()
        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    //Navigating to the Summary screen
    @Test
    fun cupcakeNavHost_clickNextOnPickUpScreen_navigatesToSummaryScreen(){
        navigateToPickupScreen()
        composeTestRule.onNodeWithText(getFormattedDate()).performClick()
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Summary.name)
    }

    //Navigating to the Start screen by clicking the Cancel button from the Summary screen
    @Test
    fun cupcakeNavHost_clickCancelOnSummaryScreen_navigatesToStartScreen(){
        navigateToSummaryScreen()
        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)

    }
    private fun navigateToFlavourScreen(){
        composeTestRule.onNodeWithStringId(R.string.one_cupcake).performClick()
        composeTestRule.onNodeWithStringId(R.string.chocolate).performClick()
    }

    private fun getFormattedDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, 1)
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        return formatter.format(calendar.time)
    }


    private fun navigateToPickupScreen(){
        navigateToFlavourScreen()
        composeTestRule.onNodeWithStringId(R.string.next)
    }

    private fun navigateToSummaryScreen(){
        navigateToPickupScreen()
        composeTestRule.onNodeWithText(getFormattedDate()).performClick()
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
    }

    private fun performNavigationUp(){
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).performClick()
    }
}

