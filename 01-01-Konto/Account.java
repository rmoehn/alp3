package alp3.ueb;

import java.util.List;

/**
 * An account, being a record/history of changes to a value.
 *
 * The types {@code Entry} and {@code Quantity} are not specified here as they
 * are secondary and our task didn't call for an implementation. However, a
 * quantity may be seen as a number with a unit associated to it and an entry
 * in the account is a quantity with a charging and a booking timepoint
 * associated to it.
 *
 * See also: Martin Fowler: Analysis Patterns. Reusable Object Models,
 * Addison Wesley, 1997. pp. 36-38, 97f., 324
 */
public interface Account {
    /**
     * Adds an entry to the record the account keeps.
     */
    public void book(Entry e);

    /**
     * Returns a list of postings made to this account.
     */
    public List<Entry> postings();

    /**
     * Returns this account's balance.
     */
    public Quantity balance();

    /**
     * Returns this account's account number.
     */
    public int accountNumber();
}
