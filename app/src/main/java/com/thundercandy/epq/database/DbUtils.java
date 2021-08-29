package com.thundercandy.epq.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.thundercandy.epq.Card;
import com.thundercandy.epq.Category;
import com.thundercandy.epq.database.CategoryContract.CardEntry;
import com.thundercandy.epq.database.CategoryContract.CategoryEntry;

import java.util.ArrayList;
import java.util.Arrays;

public class DbUtils {

    public static void addDebugData(Context context) {

        ArrayList<Category> categories = new ArrayList<>();

        Card a = new Card(1, "Work done", "The amount of force needed to move an object a certain distance.");
        Card b = new Card(2, "Momentum", "The product of the mass and velocity of an object.");
        Card c = new Card(3, "Centripetal Force", "A force, orthogonal to the direction of motion of an object which causes the object to move in a circular path");

        Card d = new Card(4, "FDE Cycle", "The FDE cycle is followed by a processor to process an instruction.");
        Card e = new Card(5, "Hardware", "The physical components of a computer system");
        Card f = new Card(6, "Software", "Instructions that tell a computer what to do");
        Card g = new Card(7, "Vector", "A quantity with both a magnitude and direction");

        categories.add(new Category(1, "Physics", new ArrayList<>(Arrays.asList(a, b, c))));
        categories.add(new Category(2, "Computer Science", new ArrayList<>(Arrays.asList(d, e, f))));
        categories.add(new Category(2, "Maths", new ArrayList<>(Arrays.asList(g))));

        Database helper = new Database(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        for (Category cat : categories) {
            ContentValues values = new ContentValues();
            values.put(CategoryEntry.COLUMN_NAME, cat.getName());
            values.put(CategoryEntry.COLUMN_DATE_CREATED, 0);                       //TODO: change this later on

            long categoryID = db.insert(CategoryEntry.TABLE_NAME, null, values);

            for (Card card : cat.getCards()) {
                values = new ContentValues();
                values.put(CardEntry.CATEGORY_ID, categoryID);
                values.put(CardEntry.COLUMN_TERM, card.getTerm());
                values.put(CardEntry.COLUMN_DEFINITION, card.getDefinition());
                values.put(CardEntry.COLUMN_DATE_CREATED, 0);                       //TODO: change this later on

                db.insert(CardEntry.TABLE_NAME, null, values);
            }

        }

    }

    public static ArrayList<Category> getCategories(Context context) {
        Database helper = new Database(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] projection = {
                CategoryEntry.COLUMN_NAME
        };

        Cursor c_cat = db.query(CategoryEntry.TABLE_NAME,
                projection, null, null, null, null, null, null);

        ArrayList<Category> categories = new ArrayList<>();

        try {
            while (c_cat.moveToNext()) {
                int cat_id = c_cat.getPosition();
                String category_name = String.valueOf(c_cat.getString(c_cat.getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME)));

                ArrayList<Card> cards = new ArrayList<>();

                projection = new String[]{
                        CardEntry.COLUMN_TERM,
                        CardEntry.COLUMN_DEFINITION,
                        CardEntry.COLUMN_DATE_CREATED
                };
                String selection = CardEntry.CATEGORY_ID + " LIKE ?";
                String[] selectionArgs = {String.valueOf(cat_id+1)};

                Cursor c_card = db.query(CardEntry.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, null, null);

                try {
                    while (c_card.moveToNext()) {
                        int card_id = c_card.getPosition();
                        String term = String.valueOf(c_card.getString(c_card.getColumnIndexOrThrow(CardEntry.COLUMN_TERM)));
                        String definition = String.valueOf(c_card.getString(c_card.getColumnIndexOrThrow(CardEntry.COLUMN_DEFINITION)));

                        cards.add(new Card(card_id, term, definition));
                    }
                } finally {
                    c_card.close();
                }

                categories.add(new Category(cat_id, category_name, cards));
            }
        } finally {
            c_cat.close();
        }

        return categories;
    }
}
