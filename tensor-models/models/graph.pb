node {
  name: "input"
  op: "Placeholder"
  attr {
    key: "dtype"
    value {
      type: DT_FLOAT
    }
  }
  attr {
    key: "shape"
    value {
      shape {
        dim {
          size: 1
        }
        dim {
          size: 10
        }
      }
    }
  }
}
node {
  name: "a_2"
  op: "Const"
  attr {
    key: "dtype"
    value {
      type: DT_FLOAT
    }
  }
  attr {
    key: "value"
    value {
      tensor {
        dtype: DT_FLOAT
        tensor_shape {
          dim {
            size: 10
          }
          dim {
            size: 5
          }
        }
        tensor_content: "\321\300\230?\254l\347\276r\203\207\276V\002\376=\232\241\320\276\331p\224\275<\355\224>\244\016\313>w7\313>v;\024?\304\267\270?\231*^\277Rg\"\277>\212\262?#\276\010@?6b=?\365\367?\214;W>\2208\022>\216\330\351\277;\024\022?%\256\253>w\203\276>\263\227\305\275\371(=?I\262\362\274\010n1>\261\337\365>_|\300\276\231\316(\277B\307\345?\364\360c>\276\376\237?\211d\007\277Op\t\277\332c\225\277\'D\031?\266\352\300?\201\264Y\277\335\222\261\277\310\347Q\277Y\304-\277Y\377\362>\315\364\233\277\234\350/?yzj\276\204\020\002\277GO~\277\"z\241>N\361N>"
      }
    }
  }
}
node {
  name: "output"
  op: "MatMul"
  input: "input"
  input: "a_2"
  attr {
    key: "T"
    value {
      type: DT_FLOAT
    }
  }
  attr {
    key: "transpose_a"
    value {
      b: false
    }
  }
  attr {
    key: "transpose_b"
    value {
      b: false
    }
  }
}
node {
  name: "init"
  op: "NoOp"
}
versions {
  producer: 8
}
