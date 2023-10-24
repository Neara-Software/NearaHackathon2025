package org.example;

import java.util.Objects;
import java.util.Random;

/// Immutable Vector2 helper class. Note: You should not attempt to mutate these vectors,
/// the members are defined as final to prevent this. Instead you should create new Vector2s
/// after each operation in your calculation.
public class Vector2 {
    private final double x;
    private final double y;

    /// Constant to multiply by to convert radians to degrees
    public static final double RAD_TO_DEG = 180.0 / Math.PI;

    /// Constant to multiply by to convert degrees to radians
    public static final double DEG_TO_RAD = Math.PI / 180.0;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /// Return a random uniform vector with both x and y ranging over [-1, 1]
    public static Vector2 randomUniform(Random random) {
        return new Vector2(2 * random.nextDouble() - 1, 2 * random.nextDouble() - 1);
    }

    public static Vector2 randomGaussian(Random random) {
        return new Vector2(random.nextGaussian(), random.nextGaussian());
    }

    /// Converts a robocode Angle into a normalized Vector2
    public static Vector2 fromAngleDegrees(double direction) {
        double theta = direction * DEG_TO_RAD;
        return new Vector2(Math.cos(theta), Math.sin(theta));
    }

    // Extracts the robocode Angle from a vector
    public double toBearing() {
        return (360 + (Math.atan2(y, x) * RAD_TO_DEG)) % 360.0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /// Vector addition
    public Vector2 add(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    /// Vector Subtraction
    public Vector2 subtract(Vector2 other) {
        return new Vector2(this.x - other.x, this.y - other.y);
    }

    /// Scale by constant
    public Vector2 scale(double scalar) {
        return new Vector2(this.x * scalar, this.y * scalar);
    }

    /// Scale x and y independently
    public Vector2 scale2(double scaleX, double scaleY) {
        return new Vector2(this.x * scaleX, this.y * scaleY);
    }

    // Rotate a Vector2 clockwise by theta (in degrees)
    public Vector2 rotateDegrees(double theta) {
        return rotate(theta * DEG_TO_RAD);
    }

    // Rotate a Vector2 clockwise by theta (in radians)
    public Vector2 rotate(double theta) {
        double cosTheta = Math.cos(theta);
        double sinTheta = Math.sin(theta);

        double newX = this.x * cosTheta - this.y * sinTheta;
        double newY = this.x * sinTheta + this.y * cosTheta;

        return new Vector2(newX, newY);
    }

    /// Dot product
    public double dot(Vector2 other) {
        return this.x * other.x + this.y * other.y;
    }

    /// Length of vector
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    /// Return normalized vector, or zero vector if input is zero
    public Vector2 normalize() {
        double mag = magnitude();
        if (mag < 1e-8) {
            return new Vector2(0, 0); // Avoid division by zero
        }
        return new Vector2(this.x / mag, this.y / mag);
    }

    /// Distance between this Vector2 and another
    public double distance(Vector2 other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Vector2 vector2 = (Vector2) obj;
        return Double.compare(vector2.x, x) == 0 && Double.compare(vector2.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Vector2(" +
                "x=" + x +
                ", y=" + y +
                ')';
    }
}
