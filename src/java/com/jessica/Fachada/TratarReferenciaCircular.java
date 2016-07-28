/*
 * Projeto SISGEPA - Sistema de Gestao de Producao Academica
 * Disciplina Projeto de Sistemas de Software
 * Professor Carlos J. P. de Lucena
 * PUC-Rio 2016.1
 */
package com.jessica.Fachada;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author Jessica
 */
public class TratarReferenciaCircular {

    private static Map<UUID, Set<Object>> CONTEXTO = new HashMap<UUID, Set<Object>>();

    private static Map<UUID, Integer> PROFUNDIDADE = new HashMap<>();
    
    /**
     * Trata a referência circular
     * @param <T>
     * @param object Objeto do qual a referência circular deve ser tratada
     * @return 
     */
    public static <T> T tratar(T object) {
        UUID context = UUID.randomUUID();
        CONTEXTO.put(context, new HashSet<>());
        PROFUNDIDADE.put(context, 0);
        
        T result = tratar(object, context);

        CONTEXTO.remove(context);
        return result;
    }

    /**
     * Trata a referência circular de uma lista
     * @param <T>
     * @param object Lista da qual a referência circular deve ser tratada
     * @return 
     */
    public static <T> List<T> tratarLista(List<T> object) {
        UUID context = UUID.randomUUID();
        CONTEXTO.put(context, new HashSet<>());
        PROFUNDIDADE.put(context, 0);
        
        List<T> result = tratarLista(object, context);

        CONTEXTO.remove(context);
        return result;
    }

    /**
     * Trata a referência circular de uma lista
     * @param <T>
     * @param object Lista da qual a referência circular deve ser tratada
     * @param uuid
     * @return 
     */
    private static <T> List<T> tratarLista(List<T> object, UUID uuid) {
        List<T> result = new ArrayList<>();
        for (T element : object) {
            result.add(tratar(element, uuid));
        }
        return result;
    }

    /**
     * Trata a referência circular através de um método recursivo
     * @param <T>
     * @param object Objeto do qual a referência circular deve ser tratada
     * @param uuid
     * @return 
     */
    private static <T> T tratar(T object, UUID uuid) {

        try {
            if (CONTEXTO.get(uuid).contains(object)) {
                return null;
            }
            if(object == null) {
                return null;
            }
            if(PROFUNDIDADE.get(uuid) > 5) {
                return null;
            }
            
            T target = (T) object.getClass().newInstance();
            CONTEXTO.get(uuid).add(object);
            Integer deep = PROFUNDIDADE.get(uuid);
            PROFUNDIDADE.remove(uuid);
            deep ++;
            PROFUNDIDADE.put(uuid, deep);
            
            for (Field field : getAllFields(new ArrayList<Field>(), object.getClass())) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                if (field.getType().isPrimitive() || field.getType().isEnum() || Number.class.isAssignableFrom(field.getType()) || String.class.isAssignableFrom(field.getType()) || Date.class.isAssignableFrom(field.getType())) {
                    field.set(target, field.get(object));
                } else if (List.class.isAssignableFrom(field.getType())) {
                    field.set(target, tratarLista((List) field.get(object), uuid));
                } else {
                    field.set(target, tratar(field.get(object), uuid));
                }
            }

            CONTEXTO.get(uuid).remove(object);
            
            deep = PROFUNDIDADE.get(uuid);
            PROFUNDIDADE.remove(uuid);
            deep --;
            PROFUNDIDADE.put(uuid, deep);
            
            return target;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Traz todos os campos de uma classe, incluindo os campos da superclasse
     * @param fields Lista de campos passada por referência
     * @param type Classe do Tipo
     * @return 
     */
    public static List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            fields = getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

}
