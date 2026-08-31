package edu.emory.diabetes.education.presentation.fragments.resources.foodResources

import androidx.annotation.DrawableRes
import edu.emory.diabetes.education.R

/** A single food option with its serving size and estimated carbs in grams. */
data class CarbFood(
    val name: String,
    val serving: String,
    val carbs: Int,
    @DrawableRes val image: Int? = null,
    val id: Int = 0,
    val isCustom: Boolean = false
)

/** A named group of foods shown as a section and as a chip in the top row. */
data class CarbCategory(
    val title: String,
    val chipLabel: String,
    val items: List<CarbFood>,
    @DrawableRes val chipImage: Int? = null
)

/** Sample carb reference data. Names, servings and carb values can be edited freely. */
val carbCategories: List<CarbCategory> = listOf(
    CarbCategory(
        title = "Grains",
        chipLabel = "Grains",
        chipImage = R.drawable.im_bread,
        items = listOf(
            CarbFood("Bread", "per slice", 15, R.drawable.im_bread),
            CarbFood("Bagel", "1/2 piece", 30, R.drawable.im_bagel),
            CarbFood("Hamburger / Hot Dog Bun", "per bun", 30, R.drawable.im_hamburger),
            CarbFood("English Muffin", "per muffin", 30, R.drawable.im_english_muffin),
            CarbFood("Tortilla (6\")", "per tortilla", 15, R.drawable.im_tortilla),
            CarbFood("Corn Bread", "per 4\" cube", 30, R.drawable.im_cornbread),
            CarbFood("Pancake", "per 4\" pancake", 15, R.drawable.im_pancake),
            CarbFood("Oatmeal / Grits", "1 cup | cooked", 30, R.drawable.im_oatmeal),
            CarbFood("Unsweetened Cereal", "3/4 cup", 15, R.drawable.im_unsweetened_cereal),
            CarbFood("Sweetened Cereal", "1 cup", 30, R.drawable.im_sweetened_cereal),
            CarbFood("Pasta", "1 cup", 45, R.drawable.im_penne_pasta),
            CarbFood("Rice", "1 cup | cooked", 45, R.drawable.im_white_rice),
            CarbFood("Waffle (Frozen / Heated)", "per waffle", 15, R.drawable.im_waffle),
            CarbFood("Dinner Roll", "per roll", 15, R.drawable.im_dinner_roll),
        )
    ),
    CarbCategory(
        title = "Milk",
        chipLabel = "Milk",
        chipImage = R.drawable.im_regular_milk,
        items = listOf(
            CarbFood("Regular Milk", "1 cup", 12, R.drawable.im_regular_milk),
            CarbFood("Chocolate Milk", "1 cup", 24, R.drawable.im_chocolate_milk),
            CarbFood("Flavored Soy / Almond Milk", "1 cup", 15, R.drawable.im_flavoured_soy),
            CarbFood("Almond Milk", "1 cup", 8, R.drawable.im_almond_milk),
            CarbFood("Chobani Kids Yogurt Tube", "1 stick", 7, R.drawable.im_yogurt_tube),
            CarbFood("Light Yogurt", "6 oz", 15, R.drawable.im_light_yogurt),
            CarbFood("Regular Yogurt", "6 oz", 30, R.drawable.im_regular_yogurt)
        )
    ),
    CarbCategory(
        title = "Starchy Vegetables",
        chipLabel = "Starchy Veg",
        chipImage = R.drawable.im_corn_on_the_cob,
        items = listOf(
            CarbFood("Baked Potato", "1 small potato", 30, R.drawable.im_baked_potato),
            CarbFood("Fast Food French Fries", "small size", 30, R.drawable.im_mashed_potatoes),
            CarbFood("Sweet Potato", "1/2 cup", 15, R.drawable.im_sweet_potato),
            CarbFood("Green Peas", "1/2 cup", 15, R.drawable.im_green_peas),
            CarbFood("Corn", "1/2 cup | 1 small corn on cob", 15, R.drawable.im_corn_on_the_cob),
            CarbFood("Black / Pinto Beans", "1/2 cup", 15, R.drawable.im_black_beans),
            CarbFood("Baked Beans", "1/2 cup", 25, R.drawable.im_baked_beans),
            CarbFood("Lima Beans", "1/2 cup", 15, R.drawable.im_chickpeas),
            CarbFood("Black-Eyed Peas", "1/2 cup", 15, R.drawable.im_black_eyed_peas),
            CarbFood("Lentils", "1/2 cup | cooked", 15, R.drawable.im_lentils),
            CarbFood("Butternut Squash", "1 cup | cooked", 25, R.drawable.im_butternut_squash),
            CarbFood("ChickPeas", "1 cup | cooked", 20, R.drawable.im_soybeans),
            CarbFood("Tater Tots", "9 pieces", 20, R.drawable.im_tater_tots)
        )
    ),
    CarbCategory(
        title = "Fruits",
        chipLabel = "Fruits",
        chipImage = R.drawable.im_apple,
        items = listOf(
            CarbFood("Orange", "small orange", 30, R.drawable.im_orange),
            CarbFood("Apple", "small apple", 15,R.drawable.im_apple),
            CarbFood("Banana", "medium banana", 30, R.drawable.im_banana),
            CarbFood("Clementines", "2 small", 15, R.drawable.im_clementines),
            CarbFood("Grapes", "15-17 grapes | 1/2 cup", 15, R.drawable.im_grapes),
            CarbFood("Pear", "1 large pear", 15, R.drawable.im_pear),
            CarbFood("Strawberries", "1 1/4 cup", 15, R.drawable.im_strawberries),
            CarbFood("Watermelon", "1 1/4 cup", 25, R.drawable.im_watermelon),
            CarbFood("Dried Fruit (Raisins)", "2 tbsp", 15, R.drawable.im_raisins),
            CarbFood("Fruit Cup in Light Syrup", "1/2 cup", 15, R.drawable.im_fruit_cocktail),
            CarbFood("Unsweetened Applesauce", "1/2 cup", 15, R.drawable.im_applesauce),
            CarbFood("Orange / Apple Juice", "1/2 cup", 25, R.drawable.im_orange_juice),
            CarbFood("Blueberries", "3/4 cup", 20, R.drawable.im_blueberries),
            CarbFood("Melon Cup", "1 cup", 20, R.drawable.im_melon),
            CarbFood("Pineapple", "1 cup", 20, R.drawable.im_pineapple)
        )
    ),
    CarbCategory(
        title = "Snacks",
        chipLabel = "Snacks",
        chipImage = R.drawable.im_oreo_cookies,
        items = listOf(
            CarbFood("Popcorn", "3 cups", 15, R.drawable.im_popcorn),
            CarbFood("Pretzel Sticks", "30 thin sticks", 15, R.drawable.im_pretzel_sticks),
            CarbFood("Goldfish", "1/2 cup | 45 pcs", 15, R.drawable.im_goldfish_crackers),
            CarbFood("Potato Chips", "1oz bag", 15, R.drawable.im_potato_chips),
            CarbFood("Lance Whole Grain Cheese/PB Sand", "1 pkt", 25, R.drawable.im_whole_grain_crackers),
            CarbFood("Nature's Valley Protein Chewy Bar", "1 bar", 15, R.drawable.im_protein_bar),
            CarbFood("Graham Cracker Squares", "3 pcs", 15, R.drawable.im_graham_crackers),
            CarbFood("Rice Cake", "1 pc", 12, R.drawable.im_rice_cake),
            CarbFood("Animal Crackers", "16", 25, R.drawable.im_animal_crackers),
            CarbFood("Chex Mix", "1/2 cup", 20, R.drawable.im_chexmix),
            CarbFood("Apple Chips", "1/4 cup", 15, R.drawable.im_apple_chips),
            CarbFood("Wheat Thins", "11 pcs", 21, R.drawable.im_wheat_thins),
            CarbFood("Cheese-it Crackers", "23 pcs", 15, R.drawable.im_cheese_crackers),
            CarbFood("Vanilla Wafers", "5pcs", 13, R.drawable.im_vanilla_wafers),
        )
    ),
    CarbCategory(
        title = "Combination foods",
        chipLabel = "Combos",
        chipImage = R.drawable.im_pepperoni_pizza,
        items = listOf(
            CarbFood("Pizza", "1 slice (1/8 of 14\")", 35, R.drawable.im_pepperoni_pizza),
            CarbFood("Meat & Cheese Taco", "1 taco", 15,R.drawable.im_taco),
            CarbFood("Chicken Nuggets", "5 pcs", 15, R.drawable.im_chicken_nuggets),
            CarbFood("Macaroni & Cheese", "1 cup", 45, R.drawable.im_mac_and_cheese),
            CarbFood("Spaghetti with Sauce", "1 cup", 50, R.drawable.im_spaghetti_meat_sauce),
            CarbFood("Fried Chicken Legs", "2 pcs", 15, R.drawable.im_fried_chicken),
            CarbFood("Sausage Biscuit", "1 pc", 25, R.drawable.im_sausage_biscuit),
            CarbFood("Grilled Cheese Sandwich", "1 pc", 30, R.drawable.im_grilled_cheese_sandwich),
            CarbFood("Soup", "1 cup", 15, R.drawable.im_chicken_noodle_soup),
            CarbFood("Sub Sandwich", "1 pc", 45, R.drawable.im_submarine_sandwich),
            CarbFood("Corndog", "1 pc", 25, R.drawable.im_corn_dog),
            CarbFood("Peanut Butter & Jelly Sandwich", "1 pc", 45, R.drawable.im_peanut_butter_jelly_sandwich),
            CarbFood("Chicken Quesadilla", "1 pc", 30, R.drawable.im_quesadilla),
            CarbFood("Popcorn Shrimp", "3/4 cup", 25, R.drawable.im_popcorn_shrimp),
        )
    ),
    CarbCategory(
        title = "Desserts & Sweets",
        chipLabel = "Sweets",
        chipImage = R.drawable.im_popsicle,
        items = listOf(
            CarbFood("Oreo / Choco Cookies", "2 small cookies", 15, R.drawable.im_oreo_cookies),
            CarbFood("Frosted Cake", "1 piece", 30, R.drawable.im_frosted_cake),
            CarbFood("Regular Ice Cream", "1/2 cup", 15, R.drawable.im_vanilla_ice_cream),
            CarbFood("Regular Frozen Yogurt", "1/2 cup", 15, R.drawable.im_frozen_yogurt),
            CarbFood("Sugar-Free Pudding", "1/2 cup", 15, R.drawable.im_chocolate_pudding),
            CarbFood("Mini Candy Bars", "3 bars", 15, R.drawable.im_mini_candy_bars),
            CarbFood("Fruit Snacks", "0.9 oz pouch", 20, R.drawable.im_fruit_snacks)
        )
    ),
    CarbCategory(
        title = "Condiments",
        chipLabel = "Condiments",
        chipImage = R.drawable.im_ketchup,
        items = listOf(
            CarbFood("Pancake Syrup", "1 tbsp", 35, R.drawable.im_pancake_syrup),
            CarbFood("Light Pancake Syrup", "2 tbsp", 15, R.drawable.im_light_pancake_syrup),
            CarbFood("Sugar-Free Pancake Syrup", "2 tbsp", 5, R.drawable.im_sugar_free_pancake_syrup),
            CarbFood("Ketchup", "1 tbsp", 5, R.drawable.im_ketchup),
            CarbFood("Sugar, Honey or Jelly", "1 tbsp", 15, R.drawable.im_honey),
            CarbFood("BBQ sauce", "1 tbsp", 7, R.drawable.im_bbq_sauce),
        )
    ),
    CarbCategory(
        title = "Low-Carb Foods",
        chipLabel = "Low Carb",
        chipImage = R.drawable.im_raw_veggies,
        items = listOf(
            CarbFood("Raw Veggies", "1 cup", 5, R.drawable.im_raw_veggies),
            CarbFood("Salad with Dressing", "2 tbsp dressing", 5, R.drawable.im_salad_with_dressing),
            CarbFood("Broccoli, Cabbage, Carrots, Celery, Collards, Cucumber, Green Beans, Salad Greens.", "1/2 cup cooked | 1 cup raw", 5, R.drawable.im_mixed_vegetables),
            CarbFood("Dill Pickles", "2 spears", 5, R.drawable.im_pickles),
            CarbFood("String Cheese / Eggs / Deli Meats", "1 serving", 5, R.drawable.im_string_cheese_eggs),
            CarbFood("Nuts", "1 handful", 5, R.drawable.im_mixed_nuts),
            CarbFood("Sunflower Seeds", "1 handful", 5, R.drawable.im_sunflower_seeds),
            CarbFood("Sugar-Free Jello", "1 cup", 5, R.drawable.im_jello),
            CarbFood("Sugar-Free Popsicles", "1 popsicle", 5, R.drawable.im_popsicle),
            CarbFood("Low-Carb Yogurt", "6 oz", 5, R.drawable.im_low_carb_yogurt)
        )
    )
)
