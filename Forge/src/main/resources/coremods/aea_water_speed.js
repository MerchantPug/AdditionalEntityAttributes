var Opcodes = Java.type('org.objectweb.asm.Opcodes')
var InsnList = Java.type('org.objectweb.asm.tree.InsnList')
var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode')
var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode')

function initializeCoreMod() {
	return {
		"aea_jump_hook": transformer('jumpInFluid', 'jumpHook'),
		"aea_sink_hook": transformer('sinkInFluid', 'sinkHook')
	}
}

function transformer(methodName, hookName) {
	return {
		'target': {
			'type': 'METHOD',
			'class': 'net.minecraftforge.common.extensions.IForgeLivingEntity',
			'methodName': methodName,
			'methodDesc': '(Lnet/minecraftforge/fluids/FluidType;)V'
		},
		'transformer': function (node) {
			var iterator = node.instructions.iterator();
			var insertionSpot = null;
			while(iterator.hasNext()) {
				var ain = iterator.next();
				if (ain.getOpcode() === Opcodes.LDC) {
					insertionSpot = ain;
					break;
				}
			}
			var insns = new InsnList();
			insns.add(new VarInsnNode(Opcodes.ALOAD, 0));
			insns.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, "net/minecraftforge/common/extensions/IForgeLivingEntity", "self", "()Lnet/minecraft/world/entity/LivingEntity;", true));
			insns.add(new VarInsnNode(Opcodes.ALOAD, 1));
			node.instructions.insertBefore(insertionSpot, insns);
			node.instructions.insert(insertionSpot, new MethodInsnNode(Opcodes.INVOKESTATIC, "de/dafuqs/additionalentityattributes/FluidTypeHooks", hookName, "(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraftforge/fluids/FluidType;D)D", false));
			return node;
		}
	}
}