import tensorflow as tf

#with tf.Graph().as_default() as g:
#    x = tf.placeholder(tf.float32, [1, 10])
a = tf.Variable(tf.random_normal([10, 5], dtype=tf.float32), name="a")
#    y = tf.matmul(x, a)

sess = tf.Session()
sess.run(tf.initialize_all_variables())

a_eval = a.eval(sess)
print a_eval
sess.close()

with tf.Graph().as_default() as g_2:

    x_2 = tf.placeholder(tf.float32, [1, 10], name="in_vector")
    a_2 = tf.constant(a_eval, name="a_2") 
    y_2 = tf.matmul(x_2, a_2, name="out_vector")

    sess_2 = tf.Session()
    sess_2.run(tf.initialize_all_variables())

    graph_def = g_2.as_graph_def()

    tf.train.write_graph(graph_def, 'models/', 'model.pb', as_text=False)