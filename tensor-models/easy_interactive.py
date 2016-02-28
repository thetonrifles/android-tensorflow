import tensorflow as tf

g = tf.Graph()
with g.as_default():

    cols = 10 #88
    rows = 5 #48

    x = tf.placeholder(tf.float32, [1, cols], name="input")
    a = tf.random_normal([cols, rows], dtype=tf.float32, name="a")
    y = tf.matmul(x, a, name="output")

    sess = tf.Session()

    print(x)
    print(a)
    print(sess.run(y, feed_dict={ x: 
        [[ 4.0, 6.0, 6.0, 3.0, 2.0, 1.0, 5.0, 6.0, 7.0, 3.0 ]] }))
    print(y)
