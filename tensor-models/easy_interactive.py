import tensorflow as tf

g = tf.Graph()
with g.as_default():

    x = tf.random_normal([1, 88], dtype=tf.float32, name="input")
    a = tf.random_normal([88, 48], dtype=tf.float32, name="a")
    y = tf.matmul(x, a, name="output")

    sess = tf.Session()

    print(x)
    print(a)
    print(sess.run(y))
    print(y)
