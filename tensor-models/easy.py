import tensorflow as tf

g = tf.Graph()
with g.as_default():
    
    x = tf.placeholder(tf.float32, [1, 88], name="input")
    a = tf.random_normal([88, 48], dtype=tf.float32, name="a") 
    y = tf.matmul(x, a, name="output")

    sess = tf.Session()

    init = tf.initialize_all_variables();
    sess.run(init)
    
    graph_def = g.as_graph_def()

    tf.train.write_graph(graph_def, 'models/', 'model.pb', as_text=False)	
