import tensorflow as tf

g = tf.Graph()
with g.as_default():

    cols = 10 #88
    rows = 5 #48	
    
    x = tf.placeholder(tf.float32, [1, cols], name="input")
    a = tf.placeholder(tf.float32, [cols, rows], name="a")
    y = tf.matmul(x, a, name="output")

    sess = tf.Session()

    init = tf.initialize_all_variables();
    sess.run(init)
    
    graph_def = g.as_graph_def()

    tf.train.write_graph(graph_def, 'models/', 'model.pb', as_text=False)	
