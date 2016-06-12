package org.jxls.common;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Date: Nov 10, 2009
 *
 * @author Leonid Vysochyn
 */
public class ContextTest {
    @Test
    public void addVars(){
        Context context = new Context();
        context.putVar("col", 1);
        context.putVar("row", 2);
        context.putVar("obj", "12345");
        Map vars = context.toMap();
        assertEquals(vars.get("col"), 1);
        assertEquals(vars.get("row"), 2);
        assertEquals(vars.get("obj"), "12345");
    }

    @Test
    public void removeVar(){
        Context context = new Context();
        context.putVar("col", 1);
        Map vars = context.toMap();
        assertEquals(vars.get("col"), 1);
        context.removeVar("col");
        vars = context.toMap();
        assertFalse( vars.containsKey("col") );
    }
    
    @Test 
    public void testToString(){
        Context context = new Context();
        context.putVar("x", 1);
        context.putVar("y", "Abc");
        assertTrue(context.toString().contains("y=Abc"));
        assertTrue(context.toString().contains("x=1"));
    }

    @Test
    public void testCreateFromMap(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("x", "Abc");
        map.put("y", 10);
        Context context = new Context(map);
        map.clear();
        assertEquals( "Abc", context.getVar("x"));
        assertEquals( 10, context.getVar("y") );
    }
}
