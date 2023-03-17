package talsumi.marderlib.registration

/**
 * Holds multiple objects that can be registered in an [EasyRegisterableHolder]. Useful for things like coloured items, blocks, etc.
 *
 * K: A key to return a specific object. Usually an enum.
 *
 * T: Type (Block, Item, etc.)
 *
 * See [ColourMultiReg] for an example.
 */
abstract class MultiReg<T: Any, K: Any> {

    /**
     * Returns the object keyed to [type]
     */
    abstract fun get(type: K): T

    /**
     * Returns all held objects, and their names. The size of the returned array must be equal to the value returned by [count].
     *
     * Important: It is expected that the same objects are returned from each call to this method. Your objects should NOT be instantiated here.
     */
    abstract fun getAll(): Array<Pair<T, String>>

    /**
     * Returns the amount of objects held.
     */
    abstract fun count(): Int

    /**
     * Returns an array of the contained objects. Like [getAll] but without keys.
     */
    abstract fun values(): Array<T>
}