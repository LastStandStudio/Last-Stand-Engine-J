package com.laststandstudio.java.engine.src.math;

/**
 * Created by joshu on 8/23/2015.
 */
public class Vec2 {

    public float x, y;

    public Vec2() {

    }

    public Vec2(float x, float y) {
        set(x, y);
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the length squared of the vector
     */
    public float lengthSquared() {
        return x * x + y * y;
    }

    /**
     * @return the length of the vector
     */
    public final float length() {
        return (float) Math.sqrt(lengthSquared());
    }


    /**
     * Translate a vector
     *
     * @param x The translation in x
     * @param y the translation in y
     * @return this
     */
    public Vec2 translate(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /**
     * Negate a vector
     *
     * @return this
     */
    public Vec2 negate() {
        x = -x;
        y = -y;
        return this;
    }

    /**
     * Negate a vector and place the result in a destination vector.
     *
     * @param dest The destination vector or null if a new vector is to be created
     * @return the negated vector
     */
    public Vec2 negate(Vec2 dest) {
        if (dest == null)
            dest = new Vec2();
        dest.x = -x;
        dest.y = -y;
        return dest;
    }


    /**
     * Normalise this vector and place the result in another vector.
     *
     * @param dest The destination vector, or null if a new vector is to be created
     * @return the normalised vector
     */
    public Vec2 normalise(Vec2 dest) {
        float l = length();

        if (dest == null)
            dest = new Vec2(x / l, y / l);
        else
            dest.set(x / l, y / l);

        return dest;
    }

    /**
     * The dot product of two vectors is calculated as
     * v1.x * v2.x + v1.y * v2.y + v1.z * v2.z
     *
     * @param left  The LHS vector
     * @param right The RHS vector
     * @return left dot right
     */
    public static float dot(Vec2 left, Vec2 right) {
        return left.x * right.x + left.y * right.y;
    }


    /**
     * Calculate the angle between two vectors, in radians
     *
     * @param a A vector
     * @param b The other vector
     * @return the angle between the two vectors, in radians
     */
    public static float angle(Vec2 a, Vec2 b) {
        float dls = dot(a, b) / (a.length() * b.length());
        if (dls < -1f)
            dls = -1f;
        else if (dls > 1.0f)
            dls = 1.0f;
        return (float) Math.acos(dls);
    }

    /**
     * Add a vector to another vector and place the result in a destination
     * vector.
     *
     * @param left  The LHS vector
     * @param right The RHS vector
     * @param dest  The destination vector, or null if a new vector is to be created
     * @return the sum of left and right in dest
     */
    public static Vec2 add(Vec2 left, Vec2 right, Vec2 dest) {
        if (dest == null)
            return new Vec2(left.x + right.x, left.y + right.y);
        else {
            dest.set(left.x + right.x, left.y + right.y);
            return dest;
        }
    }

    /**
     * Subtract a vector from another vector and place the result in a destination
     * vector.
     *
     * @param left  The LHS vector
     * @param right The RHS vector
     * @param dest  The destination vector, or null if a new vector is to be created
     * @return left minus right in dest
     */
    public static Vec2 sub(Vec2 left, Vec2 right, Vec2 dest) {
        if (dest == null)
            return new Vec2(left.x - right.x, left.y - right.y);
        else {
            dest.set(left.x - right.x, left.y - right.y);
            return dest;
        }
    }

    public Vec2 scale(float scale) {

        x *= scale;
        y *= scale;

        return this;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(64);

        sb.append("Vec2[");
        sb.append(x);
        sb.append(", ");
        sb.append(y);
        sb.append(']');
        return sb.toString();
    }

    /**
     * @return x
     */
    public final float getX() {
        return x;
    }

    /**
     * @return y
     */
    public final float getY() {
        return y;
    }

    /**
     * Set X
     *
     * @param x
     */
    public final void setX(float x) {
        this.x = x;
    }

    /**
     * Set Y
     *
     * @param y
     */
    public final void setY(float y) {
        this.y = y;
    }

}
