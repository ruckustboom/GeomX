package geomx.sdf

import geomx.transform.*
import mathx.length
import mathx.lerp
import kotlin.math.*

public fun union(d1: Double, d2: Double): Double {
    return min(d1, d2)
}

public fun subtract(d1: Double, d2: Double): Double {
    return max(-d1, d2)
}

public fun intersect(d1: Double, d2: Double): Double {
    return max(d1, d2)
}

public fun unionSmooth(d1: Double, d2: Double, k: Double): Double {
    val h = (0.5 + 0.5 * (d2 - d1) / k).coerceIn(0.0, 1.0)
    return lerp(h, d2, d1) - k * h * (1.0 - h)
}

public fun subtractSmooth(d1: Double, d2: Double, k: Double): Double {
    val h = (0.5 - 0.5 * (d2 + d1) / k).coerceIn(0.0, 1.0)
    return lerp(h, d2, -d1) + k * h * (1.0 - h)
}

public fun intersectSmooth(d1: Double, d2: Double, k: Double): Double {
    val h = (0.5 - 0.5 * (d2 - d1) / k).coerceIn(0.0, 1.0)
    return lerp(h, d2, d1) + k * h * (1.0 - h)
}

public inline fun revolve(p: Vector3D, o: Double, primitive: (Vector2D) -> Double): Double {
    val q = Vector2D(length(p.x, p.z) - o, p.y)
    return primitive(q)
}

public inline fun extrude(p: Vector3D, h: Double, primitive: (Vector2D) -> Double): Double {
    val d = primitive(Vector2D(p.x, p.y))
    val w = Vector2D(d, abs(p.z) - h)
    return min(max(w.x, w.y), 0.0) + length(max(w.x, 0.0), max(w.y, 0.0))
}

public inline fun elongate(p: Vector3D, h: Vector3D, primitive: (Vector3D) -> Double): Double {
    val q = p - p.coerceIn(-h, h)
    return primitive(q)
}

public inline fun elongate2(p: Vector3D, h: Vector3D, primitive: (Vector3D) -> Double): Double {
    val q = abs(p) - h
    return primitive(max(q, 0.0)) + min(max(q.x, max(q.y, q.z)), 0.0)
}


public inline fun round(r: Double, primitive: () -> Double): Double {
    return primitive() - r
}

public fun onion(thickness: Double, primitive: () -> Double): Double {
    return abs(primitive()) - thickness
}

public inline fun transform(p: Vector3D, t: Transformation<*>, primitive: (Vector3D) -> Double): Double {
    return primitive(t transform p)
}

public inline fun scale(p: Vector3D, s: Double, primitive: (Vector3D) -> Double): Double {
    return primitive(p / s) * s
}

public inline fun symX(p: Vector3D, primitive: (Vector3D) -> Double): Double {
    return primitive(Vector3D(abs(p.x), p.y, p.z))
}

public inline fun symXZ(p: Vector3D, primitive: (Vector3D) -> Double): Double {
    return primitive(Vector3D(abs(p.x), p.y, abs(p.z)))
}

public inline fun repeat(p: Vector3D, s: Vector3D, primitive: (Vector3D) -> Double): Double {
    return primitive(p - p.round(s))
}

public inline fun opLimitedRepetition(p: Vector3D, s: Double, l: Vector3D, primitive: (Vector3D) -> Double): Double {
    return primitive(p - (p / s).round().coerceIn(-l, l) * s)
}

public inline fun displace(p: Vector3D, primitive: (Vector3D) -> Double, displacement: (Vector3D) -> Double): Double {
    return primitive(p) + displacement(p)
}

public inline fun twist(p: Vector3D, k: Double, primitive: (Vector3D) -> Double): Double {
    val c = cos(k * p.y)
    val s = sin(k * p.y)
    val x = Basis2D(c, -s, s, c) transform Vector2D(p.x, p.z)
    return primitive(Vector3D(x.x, x.y, p.y))
}

public inline fun bendCheap(p: Vector3D, k: Double, primitive: (Vector3D) -> Double): Double {
    val c = cos(k * p.x)
    val s = sin(k * p.x)
    val m = Basis2D(c, -s, s, c) transform Vector2D(p.x, p.y)
    return primitive(Vector3D(m.x, m.y, p.z))
}
