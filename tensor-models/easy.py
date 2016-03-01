import tensorflow as tf

a = tf.Variable(tf.random_normal([10, 5], dtype=tf.float32), name="a")

s = tf.Session()
s.run(tf.initialize_all_variables())

a_eval = a.eval(s)
s.close()

with tf.Graph().as_default() as g:

    x = tf.placeholder(tf.float32, [1, 10], name="in_matrix")
    a_const = tf.constant(a_eval, name="const_a") 
    y = tf.matmul(x, a_const, name="out_matrix")

    s = tf.Session()
    s.run(tf.initialize_all_variables())

    graph_def = g.as_graph_def()

    tf.train.write_graph(graph_def, 'models/', 'model.pb', as_text=False)