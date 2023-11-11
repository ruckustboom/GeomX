package geomx.sdf

import geomx.transform.Basis2D
import geomx.transform.Vector2D
import geomx.transform.Vector3D
import geomx.transform.transform
import kotlin.math.*

public fun circle(p: Vector2D, r: Double): Double {
    return p.length() - r
}

public fun box(p: Vector2D, b: Vector2D): Double {
    val d = abs(p) - b
    return max(d, 0.0).length() + min(max(d.x, d.y), 0.0)
}

public fun boxRound(p: Vector2D, b: Vector2D, r: Double): Double {
    val q = abs(p) - b + r
    return min(max(q.x, q.y), 0.0) + max(q, 0.0).length() - r
}

public fun boxOriented(p: Vector2D, a: Vector2D, b: Vector2D, th: Double): Double {
    val l = (b - a).length()
    val d = (b - a) / l
    val q = abs(Basis2D(d.x, -d.y, d.y, d.x) transform (p - (a + b) * 0.5)) - Vector2D(l, th) * 0.5
    return max(q, 0.0).length() + min(max(q.x, q.y), 0.0)
}

public fun segment(p: Vector2D, a: Vector2D, b: Vector2D): Double {
    val pa = p - a
    val ba = b - a
    val h = ((pa dot ba) / (ba dot ba)).coerceIn(0.0, 1.0)
    return (pa - ba * h).length()
}

public fun rhombus(p: Vector2D, b: Vector2D): Double {
    val p1 = abs(p)
    val h = ((b - p1 * 2.0 ndot b) / (b dot b)).coerceIn(-1.0, 1.0)
    val d = (p1 - b * 0.5 * Vector2D(1.0 - h, 1.0 + h)).length()
    return d * sign(p1.x * b.y + p1.y * b.x - b.x * b.y)
}

public fun trapezoid(p: Vector2D, r1: Double, r2: Double, he: Double): Double {
    val k1 = Vector2D(r2, he)
    val k2 = Vector2D(r2 - r1, 2.0 * he)
    val p1 = Vector2D(abs(p.x), p.y)
    val ca = Vector2D(p1.x - min(p1.x, if (p1.y < 0.0) r1 else r2), abs(p1.y) - he)
    val cb = p1 - k1 + k2 * ((k1 - p1 dot k2) / (k2 dot k2)).coerceIn(0.0, 1.0)
    val s = if (cb.x < 0.0 && ca.y < 0.0) -1.0 else 1.0
    return s * sqrt(min(ca dot ca, cb dot cb))
}

public fun parallelogram(p: Vector2D, wi: Double, he: Double, sk: Double): Double {
    val e = Vector2D(sk, he)
    var p1 = if (p.y < 0.0) -p else p
    var w = p1 - e
    w = Vector2D(w.x - w.x.coerceIn(-wi, wi), w.y)
    var d = Vector2D(w dot w, -w.y)
    val s = p1.x * e.y - p1.y * e.x
    p1 = if (s < 0.0) -p1 else p1
    var v = p1 - Vector2D(wi, 0.0)
    v -= e * ((v dot e) / (e dot e)).coerceIn(-1.0, 1.0)
    d = min(d, Vector2D(v dot v, wi * he - abs(s)))
    return sqrt(d.x) * sign(-d.y)
}

public fun triangleEquilateral(p: Vector2D, r: Double): Double {
    val k = sqrt(3.0)
    var p1 = Vector2D(abs(p.x) - r, p.y + r / k)
    if (p1.x + k * p1.y > 0.0) p1 = Vector2D(p1.x - k * p1.y, -k * p1.x - p1.y) / 2.0
    p1 = Vector2D(p1.x - p1.x.coerceIn(-2.0 * r, 0.0), p1.y)
    return -p1.length() * sign(p1.y)
}

public fun triangleIsosceles(p: Vector2D, q: Vector2D): Double {
    val p1 = Vector2D(abs(p.x), p.y)
    val a = p1 - q * ((p1 dot q) / (q dot q)).coerceIn(0.0, 1.0)
    val b = p1 - q * Vector2D((p1.x / q.x).coerceIn(0.0, 1.0), 1.0)
    val s = -sign(q.y)
    val d = min(
        Vector2D(a dot a, s * (p1.x * q.y - p1.y * q.x)),
        Vector2D(b dot b, s * (p1.y - q.y)),
    );
    return -sqrt(d.x) * sign(d.y)
}

public fun triangle(p: Vector2D, p0: Vector2D, p1: Vector2D, p2: Vector2D): Double {
    val e0 = p1 - p0
    val e1 = p2 - p1
    val e2 = p0 - p2
    val v0 = p - p0
    val v1 = p - p1
    val v2 = p - p2
    val pq0 = v0 - e0 * ((v0 dot e0) / (e0 dot e0)).coerceIn(0.0, 1.0)
    val pq1 = v1 - e1 * ((v1 dot e1) / (e1 dot e1)).coerceIn(0.0, 1.0)
    val pq2 = v2 - e2 * ((v2 dot e2) / (e2 dot e2)).coerceIn(0.0, 1.0)
    val s = sign(e0.x * e2.y - e0.y * e2.x);
    val d = min(
        min(
            Vector2D(pq0 dot pq0, s * (v0.x * e0.y - v0.y * e0.x)),
            Vector2D(pq1 dot pq1, s * (v1.x * e1.y - v1.y * e1.x)),
        ),
        Vector2D(pq2 dot pq2, s * (v2.x * e2.y - v2.y * e2.x)),
    )
    return -sqrt(d.x) * sign(d.y);
}

public fun capsuleUneven(p: Vector2D, r1: Double, r2: Double, h: Double): Double {
    val p1 = Vector2D(abs(p.x), p.y)
    val b = (r1 - r2) / h
    val a = sqrt(1.0 - b * b)
    val k = p1 dot Vector2D(-b, a)
    if (k < 0.0) return p1.length() - r1
    return if (k > a * h) (p1 - Vector2D(0.0, h)).length() - r2 else (p1 dot Vector2D(a, b)) - r1
}

public fun pentagon(p: Vector2D, r: Double): Double {
    val k = Vector3D(0.809016994, 0.587785252, 0.726542528)
    var p1 = Vector2D(abs(p.x), p.y)
    p1 -= Vector2D(-k.x, k.y) * (2.0 * min(Vector2D(-k.x, k.y) dot p1, 0.0))
    p1 -= Vector2D(k.x, k.y) * (2.0 * min(Vector2D(k.x, k.y) dot p1, 0.0))
    p1 -= Vector2D(p1.x.coerceIn(-r * k.z, r * k.z), r)
    return p1.length() * sign(p1.y)
}

public fun hexagon(p: Vector2D, r: Double): Double {
    val k = Vector3D(-0.866025404, 0.5, 0.577350269)
    var p1 = abs(p)
    p1 -= Vector2D(k.x, k.y) * (2.0 * min(Vector2D(k.x, k.y) dot p1, 0.0))
    p1 -= Vector2D(p1.x.coerceIn(-k.z * r, k.z * r), r)
    return p1.length() * sign(p1.y)
}

public fun octagon(p: Vector2D, r: Double): Double {
    val k = Vector3D(-0.9238795325, 0.3826834323, 0.4142135623)
    var p1 = abs(p)
    p1 -= Vector2D(k.x, k.y) * (2.0 * min(Vector2D(k.x, k.y) dot p1, 0.0))
    p1 -= Vector2D(-k.x, k.y) * (2.0 * min(Vector2D(-k.x, k.y) dot p1, 0.0))
    p1 -= Vector2D(p1.x.coerceIn(-k.z * r, k.z * r), r)
    return p1.length() * sign(p1.y)
}

// TODO: Hexagram https://iquilezles.org/articles/distfunctions2d/
