package geomx.transform

import mathx.length
import mathx.lerp

public data class Vector2D(
    public val x: Double,
    public val y: Double,
) : Transformation<Vector2D> {
    override val tx: Double get() = x
    override val ty: Double get() = y
    override val tw: Double get() = 0.0

    public operator fun plus(v: Vector2D): Vector2D = Vector2D(x = x + v.x, y = y + v.y)
    public operator fun minus(v: Vector2D): Vector2D = Vector2D(x = x - v.x, y = y - v.y)
    public operator fun times(s: Double): Vector2D = Vector2D(x = x * s, y = y * s)
    public operator fun div(s: Double): Vector2D = Vector2D(x = x / s, y = y / s)

    @JvmName("negate")
    public operator fun unaryMinus(): Vector2D = Vector2D(x = -x, y = -y)

    public fun length(): Double = length(x = x, y = y)

    public fun normalize(): Vector2D {
        val len = length()
        return if (len == 0.0) ZERO else div(len)
    }

    public infix fun dot(v: Vector2D): Double = x * v.x + y * v.y

    public fun interpolate(b: Vector2D, t: Double): Vector2D = Vector2D(
        x = lerp(x, b.x, t),
        y = lerp(y, b.y, t),
    )

    override fun transformBy(t: Transformation<*>): Vector2D = Vector2D(
        x = t tx this,
        y = t ty this,
    )

    public companion object : Transformation.Builder<Vector2D> {
        public val ZERO: Vector2D = Vector2D(x = 0.0, y = 0.0)
        public val X: Vector2D = x()
        public val Y: Vector2D = y()

        public val POSITIVE_INFINITY: Vector2D = Vector2D(
            x = Double.POSITIVE_INFINITY,
            y = Double.POSITIVE_INFINITY,
        )
        public val NEGATIVE_INFINITY: Vector2D = Vector2D(
            x = Double.NEGATIVE_INFINITY,
            y = Double.NEGATIVE_INFINITY,
        )
        public val NaN: Vector2D = Vector2D(
            x = Double.NaN,
            y = Double.NaN,
        )

        public fun x(x: Double = 1.0): Vector2D = Vector2D(x = x, y = 0.0)
        public fun y(y: Double = 1.0): Vector2D = Vector2D(x = 0.0, y = y)

        override fun build(
            xx: Double, xy: Double, xz: Double, xw: Double,
            yx: Double, yy: Double, yz: Double, yw: Double,
            zx: Double, zy: Double, zz: Double, zw: Double,
            tx: Double, ty: Double, tz: Double, tw: Double,
        ): Vector2D = Vector2D(x = tx, y = ty)

        override fun toString(): String = "Vector2D"
    }
}
