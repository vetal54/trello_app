package spd.trello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spd.trello.Helper;
import spd.trello.domian.Workspace;

@SpringBootTest
public class NewWorkspace extends AbstractIntegrationTest<Workspace> {

    private final String URL_TEMPLATE = "/workspace";

    @Autowired
    private Helper helper;

//    @Test
//    void createFailure() throws Exception {
//        Workspace entity = new Workspace();
//        MvcResult mvcResult = super.create(URL_TEMPLATE, entity);
//
//        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
//    }

//    @Test
//    void findById() throws Exception {
//        Workspace board = helper.createWorkspace();
//        MvcResult mvcResult = super.getById(URL_TEMPLATE, board.getId());
//
////        assertAll(
////                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
////                () -> assertNotNull(getValue(mvcResult, "$.id")),
////                () -> assertEquals(board.getCreatedBy(), getValue(mvcResult, "$.createdBy")),
////                () -> assertEquals(String.valueOf(LocalDate.now()), getValue(mvcResult, "$.createdDate")),
////                () -> assertNull(getValue(mvcResult, "$.updatedBy")),
////                () -> assertNull(getValue(mvcResult, "$.updatedDate")),
////                () -> assertEquals(board.getName(), getValue(mvcResult, "$.name")),
////                () -> assertNull(getValue(mvcResult, "$.description")),
////                () -> assertEquals(board.getVisibility().toString(), getValue(mvcResult, "$.visibility")),
////                () -> assertFalse((Boolean) getValue(mvcResult, "$.archived")),
////                () -> assertEquals(board.getWorkspaceId().toString(), getValue(mvcResult, "$.workspaceId")),
////                () -> assertTrue(testMembersIds.contains(board.getMembersIds().iterator().next())),
////                () -> assertEquals(1, testMembersIds.size())
////        );
//    }
//
//    @Test
//    void findAll() throws Exception {
//        Workspace firstBoard = helper.createWorkspace();
//        Workspace secondBoard = helper.createWorkspace();
//        MvcResult mvcResult = super.getAll(URL_TEMPLATE);
////        List<Workspace> testWorkspaces = helper.getWorkspaceArray(mvcResult);
//
////        assertAll(
////                () -> assertEquals(MediaType.APPLICATION_JSON.toString(), mvcResult.getResponse().getContentType()),
////                () -> assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus()),
////                () -> assertTrue(testWorkspaces.contains(firstBoard)),
////                () -> assertTrue(testWorkspaces.contains(secondBoard))
////        );
//    }
}
